import { Component, OnDestroy, OnInit } from '@angular/core';
import { SessionService } from "../../core/services/session.service";
import { MatSnackBar } from "@angular/material/snack-bar";
import { TopicService } from "../../core/services/topic.service";
import { Observable, Subscription } from "rxjs";
import { User } from "../../core/models/user.interface";
import { SubscriptionService } from "../../core/services/subscription.service";
import { Topic } from 'src/app/core/models/topic.interface';

@Component({
    selector: 'app-theme',
    templateUrl: './theme.component.html',
    styleUrls: ['./theme.component.scss']
})
/**
 * Composant de thème
 * @class
 * @implements {OnInit}
 * @implements {OnDestroy}
 * @public
 */
export class ThemeComponent implements OnInit, OnDestroy {

    /**
     * Observable des thèmes
     * @type {Observable<Topic[]>}
     */
    private topics$: Observable<Topic[]> = this.themeService.getNotSubscribed();
    /**
     * Liste des thèmes
     * @type {Topic[]}
     */
    public topics: Topic[] = [];
    /**
     * Subscription au service de thème
     * @type {Subscription}
     */
    private themeSubscription: Subscription = new Subscription();

    constructor(
        private matSnackBar: MatSnackBar,
        private themeService: TopicService,
        private subscriptionService: SubscriptionService,
    ) {
    }

    ngOnInit(): void {
        this.themeSubscription.add(this.topics$.subscribe((response) => {
            this.topics = response;
        }));
    }

    ngOnDestroy(): void {
        this.themeSubscription.unsubscribe();
    }

    /**
     * S'abonner à un thème
     * @param theme 
     */
    public subscribe(theme: Topic) {
        this.themeSubscription.add(this.subscriptionService.subscribe(theme.id).subscribe({
            next: () => {
                this.matSnackBar.open(`Abonné au thème: ${theme.title}`, 'Close', {
                    duration: 3000,
                });
                this.topics = this.topics.filter((t: Topic) => t.id !== theme.id);
            },
            error: () => {
                this.matSnackBar.open('Une erreur est survenue', 'OK', {
                    duration: 2000,
                });
            }
        }));
    }
}
