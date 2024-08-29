import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { SubscriptionService } from "../../../core/services/subscription.service";
import { UserService } from "../../../core/services/user.service";
import { SessionService } from "../../../core/services/session.service";
import { SubscriptionInterface } from "../../../core/models/subscription.interface";
import { Router } from "@angular/router";
import { StrongPasswordRegx } from "../../../core/constants/strong-password-regex";
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-me',
    templateUrl: './me.component.html',
    styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit, OnDestroy {

    /**
     * Indique si le mot de passe est caché
     */
    public hide = true;

    /**
     * Indique si une erreur est survenue lors de la connexion
     */
    public onError = false;

    public profileForm: FormGroup = this.fb.group({
        id: this.sessionService!.user?.id,
        username: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.pattern(StrongPasswordRegx)],
    });

    get passwordFormField() {
        return this.profileForm.get('password');
    }

    public subscriptions: SubscriptionInterface[] = [];

    private userSubscriptionMe!: Subscription;
    private subSubscription!: Subscription;
    private subSubscription2!: Subscription;
    private userUpdateSubscription!: Subscription;

    constructor(
        private fb: FormBuilder,
        private subscriptionService: SubscriptionService,
        private userService: UserService,
        private sessionService: SessionService,
        private matSnackBar: MatSnackBar,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.userSubscriptionMe = this.userService.getMe().subscribe((user) => {
            this.profileForm.patchValue(user);
        });

        this.subSubscription = this.subscriptionService.getSubscriptions().subscribe((subscriptions) => {
            this.subscriptions = subscriptions;
        });
    }

    ngOnDestroy(): void {
        if (this.userSubscriptionMe) {
            this.userSubscriptionMe.unsubscribe();
        }
        if (this.subSubscription) {
            this.subSubscription.unsubscribe();
        }
        if (this.subSubscription2) {
            this.subSubscription2.unsubscribe();
        }
        if (this.userUpdateSubscription) {
            this.userUpdateSubscription.unsubscribe();
        }
    }

    public save(): void {
        if (this.profileForm.get('password')!.value !== '' && !StrongPasswordRegx.test(this.profileForm.get('password')!.value)) {
            this.matSnackBar.open('Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.', 'Fermer', {
                duration: 5000,
            });
            return;
        }
        this.userUpdateSubscription = this.userService.update(this.profileForm.value).subscribe({
            next: () => {
                this.matSnackBar.open('Profil mis à jour ! Veuillez vous reconnecter.', 'Fermer', {
                    duration: 5000,
                });
                this.logOut();
            },
            error: (error: any) => {
                this.userUpdateSubscription.unsubscribe();
                this.matSnackBar.open(error.error.message, 'Fermer', {
                    duration: 5000,
                });
            },
            complete: () => {
                this.userUpdateSubscription.unsubscribe();
            }
        });
    }

    public unsubscribe(subscription: SubscriptionInterface): void {
        this.subSubscription2 = this.subscriptionService.unsubscribe(subscription.id).subscribe({
            next: () => {
                this.subscriptions = this.subscriptions.filter((sub) => sub.id !== subscription.id);
                this.matSnackBar.open('Désabonner à: ' + subscription.topic.title, 'Fermer', {
                    duration: 5000,
                });
            },
            error: (error: any) => {
                this.subSubscription2.unsubscribe();
                this.matSnackBar.open("Erreur lors de la désinscription", 'Fermer', {
                    duration: 5000,
                });
            },
            complete: () => {
                this.subSubscription2.unsubscribe();
            }
        });
    }

    public logOut(): void {
        this.sessionService.logOut();
        this.router.navigate(['/']);
    }
}
