import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SubscriptionService} from "../../../core/services/subscription.service";
import {UserService} from "../../../core/services/user.service";
import {SessionService} from "../../../core/services/session.service";
import {SubscriptionInterface} from "../../../core/models/subscription.interface";

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
   ) {
   }

   ngOnInit(): void {
      this.userService.getUserById(this.sessionService.user!.id).subscribe((user) => {
         this.profileForm.patchValue(user);
      });
      console.log(this.profileForm.value);
   }

   public save(): void {
   }

   public unsubscribe(subscription: SubscriptionInterface): void {
      this.subscriptionService.unsubscribe(subscription.id).subscribe(() => {
         this.subscriptions = this.subscriptions.filter((sub) => sub.id !== subscription.id);
         this.matSnackBar.open('Subscription cancelled', 'Close', {
            duration: 5000,
         });
      });
   }
}
