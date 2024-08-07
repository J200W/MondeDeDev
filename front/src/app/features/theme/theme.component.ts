import {Component, OnInit} from '@angular/core';
import {SessionService} from "../../core/services/session.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {TopicService} from "../../core/services/topic.service";
import {Observable} from "rxjs";
import {User} from "../../core/models/user.interface";
import {ThemeInterface} from "../../core/models/theme.interface";
import {SubscriptionService} from "../../core/services/subscription.service";

@Component({
   selector: 'app-theme',
   templateUrl: './theme.component.html',
   styleUrls: ['./theme.component.scss']
})
export class ThemeComponent implements OnInit {

   private topics$: Observable<ThemeInterface[]> = this.themeService.getNotSubscribed();
   public topics: any = [];
   private user = this.sessionService.user as User;

   constructor(
       private sessionService: SessionService,
       private matSnackBar: MatSnackBar,
       private themeService: TopicService,
       private subscriptionService: SubscriptionService,
   ) {
   }

   ngOnInit(): void {
      this.topics$.subscribe((response) => {
         this.topics = response;
      });
   }

   public subscribe(theme: ThemeInterface) {
      this.subscriptionService.subscribe(theme.id, this.sessionService.user?.id).subscribe(() => {
         this.matSnackBar.open(`Abonné au thème: ${theme.title}`, 'Close', {
            duration: 3000,
         });
         this.topics = this.topics.filter((t: ThemeInterface) => t.id !== theme.id);
      });
   }
}
