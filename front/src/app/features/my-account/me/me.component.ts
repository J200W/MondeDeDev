import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { SubscriptionService } from "../../../core/services/subscription.service";
import { SessionService } from "../../../core/services/session.service";
import { SubscriptionInterface } from "../../../core/models/subscription.interface";
import { Router } from "@angular/router";
import { StrongPasswordRegx } from "../../../core/constants/strong-password-regex";
import { AuthService } from '../../authentification/service/auth.service';
import { Subscription } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'app-me',
    templateUrl: './me.component.html',
    styleUrls: ['./me.component.scss']
})
/**
 * Composant de profil
 * @class
 * @implements {OnInit}
 * @implements {OnDestroy}
 * @public
 */
export class MeComponent implements OnInit, OnDestroy {

    /**
     * Indique si le mot de passe est caché
     */
    public hide = true;

    /**
     * Indique si une erreur est survenue lors de la connexion
     */
    public onError = false;

    /**
     * Formulaire de profil
     * @type {FormGroup}
     */
    public profileForm: FormGroup = this.fb.group({
        username: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.pattern(StrongPasswordRegx)],
    });

    /**
     * Formulaire de profil
     */
    get passwordFormField() {
        return this.profileForm.get('password');
    }

    /**
     * Liste des abonnements
     * @type {SubscriptionInterface[]}
     */
    public subscriptions: SubscriptionInterface[] = [];

    /**
     * Subscription au service d'abonnement
     * @type {Subscription}
     */
    private meSubscription: Subscription = new Subscription();

    constructor(
        private fb: FormBuilder,
        private subscriptionService: SubscriptionService,
        private sessionService: SessionService,
        private matSnackBar: MatSnackBar,
        private router: Router,
        private authService: AuthService,
    ) { }

    ngOnInit(): void {
        this.meSubscription.add(this.authService.me().subscribe((user) => {
            this.profileForm.patchValue(user);
        }));
        this.meSubscription.add(this.subscriptionService.getSubscriptions().subscribe((subscriptions) => {
            this.subscriptions = subscriptions;
        }));
    }

    ngOnDestroy(): void {
        this.meSubscription.unsubscribe();
    }

    /**
     * Envoie le formulaire de profil
     */
    public save(): void {
        if (this.profileForm.get('password')!.value !== null &&
            this.profileForm.get('password')!.value.trim() !== '' &&
            !StrongPasswordRegx.test(this.profileForm.get('password')!.value)) {
            this.matSnackBar.open('Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.', 'Fermer', {
                duration: 5000,
            });
            return;
        }

        if (this.profileForm.get('email')!.value.trim() === '' ||
            this.profileForm.get('username')!.value.trim() === '') {
            this.matSnackBar.open('Veuillez remplir tous les champs du formulaire', 'Fermer', {
                duration: 5000,
            });
            return;
        }
        this.meSubscription.add(this.authService.update(this.profileForm.value).subscribe({
            next: () => {
                this.matSnackBar.open('Profil mis à jour ! Veuillez vous reconnecter.', 'Fermer', {
                    duration: 5000,
                });
                this.logOut();
            },
            error: (error: HttpErrorResponse) => {
                this.matSnackBar.open(error.error.message, 'Fermer', {
                    duration: 5000,
                });
            }
        }));
    }

    /**
     * Désabonne l'utilisateur
     * @param {SubscriptionInterface} subscription
     */
    public unsubscribe(subscription: SubscriptionInterface): void {
        this.meSubscription.add(this.subscriptionService.unsubscribe(subscription.topic.url).subscribe({
            next: () => {
                this.subscriptions = this.subscriptions.filter((sub) => sub.topic.url !== subscription.topic.url);
                this.matSnackBar.open('Désabonner à: ' + subscription.topic.title, 'Fermer', {
                    duration: 5000,
                });
            },
            error: (error: HttpErrorResponse) => {
                this.matSnackBar.open("Erreur: Impossible de supprimer l'abonnement", 'Fermer', {
                    duration: 5000,
                });
            }
        }));
    }

    /**
     * Déconnecte l'utilisateur
     */
    public logOut(): void {
        this.meSubscription.add(this.authService.logout().subscribe({
            next: () => {
                this.sessionService.logOut();
                this.router.navigate(['/']);
            },
            error: (error: HttpErrorResponse) => {
                console.error(error);
            },
            complete: () => {
                this.meSubscription.unsubscribe();
            }
        }));
    }
}
