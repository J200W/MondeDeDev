import { Component, OnDestroy } from '@angular/core';
import { Subscription } from "rxjs";
import { FormBuilder, Validators } from "@angular/forms";
import { SessionService } from "../../../../core/services/session.service";
import { Router } from "@angular/router";
import { AuthService } from "../../service/auth.service";
import { LoginRequest } from "../../interfaces/loginRequest.interface";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnDestroy {

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
    private authSubscription: Subscription = new Subscription();

    constructor(private authService: AuthService,
        private fb: FormBuilder,
        private router: Router,
        private sessionService: SessionService) {
    }

    ngOnDestroy(): void {
        this.authSubscription.unsubscribe();
    }

    public submit(): void {
        const loginRequest = this.form.value as LoginRequest;
        this.authSubscription.add(this.authService.login(loginRequest).subscribe({
            next: (_) => {
                this.sessionService.logIn();
                this.router.navigate(['/article']);
            },
            error: () => {
                this.onError = true;
            }
        }));
    }
}