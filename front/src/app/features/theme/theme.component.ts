import { Component, OnDestroy, OnInit } from '@angular/core';
import { SessionService } from "../../core/services/session.service";
import { MatSnackBar } from "@angular/material/snack-bar";
import { TopicService } from "../../core/services/topic.service";
import { Observable, Subscription } from "rxjs";
import { User } from "../../core/models/user.interface";
import { ThemeInterface } from "../../core/models/theme.interface";
import { SubscriptionService } from "../../core/services/subscription.service";

@Component({
    selector: 'app-theme',
    templateUrl: './theme.component.html',
    styleUrls: ['./theme.component.scss']
})
export class ThemeComponent implements OnInit, OnDestroy {

    private topics$: Observable<ThemeInterface[]> = this.themeService.getNotSubscribed();
    public topics: any = [];
    private user = this.sessionService.user as User;
    private topicSubscription!: Subscription;
    private subSubscription!: Subscription;

    constructor(
        private sessionService: SessionService,
        private matSnackBar: MatSnackBar,
        private themeService: TopicService,
        private subscriptionService: SubscriptionService,
    ) {
    }

    ngOnInit(): void {
        this.topicSubscription = this.topics$.subscribe((response) => {
            this.topics = response;
        });
    }

    ngOnDestroy(): void {
        this.topicSubscription.unsubscribe();
        if (this.subSubscription) {
            this.subSubscription.unsubscribe();
        }
    }

    public subscribe(theme: ThemeInterface) {
        this.subSubscription = this.subscriptionService.subscribe(theme.id, this.sessionService.user?.id).subscribe({
            next: () => {
                this.matSnackBar.open(`Abonné au thème: ${theme.title}`, 'Close', {
                    duration: 3000,
                });
                this.topics = this.topics.filter((t: ThemeInterface) => t.id !== theme.id);
            },
            error: () => {
                this.subSubscription.unsubscribe();
            },
            complete: () => {
                this.subSubscription.unsubscribe();
            }
        });
    }
}
