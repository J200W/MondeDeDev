import {Component, OnInit} from '@angular/core';
import {SessionService} from "../../services/session.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ThemeService} from "../services/theme.service";
import {Observable} from "rxjs";
import {User} from "../../interfaces/user.interface";
import {ThemeInterface} from "../interfaces/theme.interface";
import {SubscriptionService} from "../services/subscription.service";

@Component({
   selector: 'app-theme',
   templateUrl: './theme.component.html',
   styleUrls: ['./theme.component.scss']
})
export class ThemeComponent implements OnInit {

   private topics$: Observable<ThemeInterface[]> = this.themeService.getThemes();
   private topics: any = [];
   private user = this.sessionService.user as User;

   constructor(
       private sessionService: SessionService,
       private matSnackBar: MatSnackBar,
       private themeService: ThemeService,
       private subscriptionService: SubscriptionService,
   ) {
   }

   ngOnInit(): void {
      this.topics$.subscribe((response) => {
         this.topics = response;
      });
   }

   public getThemes(): ThemeInterface[] {
      return this.topics;
   }

   public subscribe(theme: ThemeInterface) {
      this.subscriptionService.subscribe(theme.id, this.sessionService.user?.id).subscribe(() => {
         this.matSnackBar.open(`Abonné au thème: ${theme.title} !`, 'Close', {
            duration: 3000,
         });
      });
   }
}
