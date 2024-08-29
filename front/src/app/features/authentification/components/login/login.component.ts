import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from "rxjs";
import { FormBuilder, Validators } from "@angular/forms";
import { SessionService } from "../../../../core/services/session.service";
import { Router } from "@angular/router";
import { AuthService } from "../../service/auth.service";
import { AuthSuccess } from "../../interfaces/authSuccess.interface";
import { LoginRequest } from "../../interfaces/loginRequest.interface";
import { User } from "../../../../core/models/user.interface";
import { CookieService } from "ngx-cookie-service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

    /**
     * Indique si le mot de passe est caché
     */
    public hide = true;

    /**
     * Indique si une erreur est survenue lors de la connexion
     */
    public onError = false;
    /**
     * Formulaire de connexion
     */
    public form = this.fb.group({
        emailOrUsername: ['', [Validators.required]],
        password: ['', [Validators.required, Validators.min(3)]],
    });
    /**
     * Subscription au service d'authentification
     */
    private authSubscription!: Subscription;

    private authSubscriptionMe!: Subscription;

    constructor(private authService: AuthService,
        private fb: FormBuilder,
        private router: Router,
        private cookieService: CookieService,
        private sessionService: SessionService) {
    }

    ngOnInit(): void {
        this.sessionService.autoLogIn();
    }

    ngOnDestroy(): void {
        if (this.authSubscription) {
            this.authSubscription.unsubscribe();
        }
        if (this.authSubscriptionMe) {
            this.authSubscriptionMe.unsubscribe();
        }
    }

    public submit(): void {
        const loginRequest = this.form.value as LoginRequest;
        this.authSubscription = this.authService.login(loginRequest).subscribe({
            next: (response: AuthSuccess) => {
                this.cookieService.set('token', response.accessToken);
                this.authSubscriptionMe = this.authService.me().subscribe((user: User) => {
                    this.sessionService.logIn(user);
                    this.router.navigate(['/article']);
                    this.authSubscription.unsubscribe();
                });
                this.router.navigate(['/article']);
            },
            error: () => {
                this.onError = true;
                this.authSubscription.unsubscribe();
            },
            complete: () => {
                this.authSubscription.unsubscribe();
            }
        });
    }

}