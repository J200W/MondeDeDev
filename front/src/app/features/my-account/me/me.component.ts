import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SubscriptionService} from "../../../core/services/subscription.service";
import {UserService} from "../../../core/services/user.service";
import {SessionService} from "../../../core/services/session.service";
import {SubscriptionInterface} from "../../../core/models/subscription.interface";
import {Router} from "@angular/router";

@Component({
   selector: 'app-me',
   templateUrl: './me.component.html',
   styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

   public profileForm: FormGroup = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
   });

   public subscriptions: SubscriptionInterface[] = [];

   constructor(
       private fb: FormBuilder,
       private subscriptionService: SubscriptionService,
       private userService: UserService,
       private sessionService: SessionService,
       private matSnackBar: MatSnackBar,
       private router: Router
   ) {
   }

   ngOnInit(): void {
      this.userService.getMe().subscribe((user) => {
         this.profileForm.patchValue(user);
      });

   }

   public save(): void {
      this.userService.update(this.profileForm.value).subscribe({
         next: () => {
            this.matSnackBar.open('Profil mis à jour ! Veuillez vous reconnecter.', 'Fermer', {
               duration: 5000,
            });
            this.logOut();
         },
         error: (error: any) => {
            console.log(error);
            this.matSnackBar.open(error.error.message, 'Fermer', {
               duration: 5000,
            });
         }
      });
   }

   public unsubscribe(subscription: SubscriptionInterface): void {
      this.subscriptionService.unsubscribe(subscription.id).subscribe(() => {
         this.subscriptions = this.subscriptions.filter((sub) => sub.id !== subscription.id);
         this.matSnackBar.open('Désabonner à: ' + subscription.topic.title, 'Fermer', {
            duration: 5000,
         });
      });
   }

   public logOut(): void {
      this.sessionService.logOut();
      this.router.navigate(['/']);
   }
}
