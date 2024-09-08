import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { AuthService } from "../../service/auth.service";
import { Router } from "@angular/router";
import { SessionService } from "../../../../core/services/session.service";
import { RegisterRequest } from "../../interfaces/registerRequest.interface";
import { StrongPasswordRegx } from "../../../../core/constants/strong-password-regex";
import { ResponseAPI } from '../../interfaces/responseApiSuccess.interface';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
/**
 * Composant d'inscription
 * @class
 * @implements {OnDestroy}
 * @public
 */
export class RegisterComponent implements OnDestroy {

    /**
     * Indique si le mot de passe est caché
     */
    public hide = true;
    /**
     * Indique si le mot de passe est caché
     */
    public hide = true;

    /**
     * Indique si une erreur est survenue lors de l'inscription
     * @type {boolean}
     * @memberof RegisterComponent
     * @default false
     * @public
     *
     */
    public onError: boolean = false;
    /**
     * Message d'erreur
     * @type {string}
     * @memberof RegisterComponent
     * @public
     *
     */
    public errorMessage: string = '';
    /**
     * Formulaire d'inscription
     * @type {FormGroup}
     * @memberof RegisterComponent
     * @public
     *
     */

    public form: FormGroup = this.fb.group({
        email: ['', [Validators.required, Validators.email]],
        username: ['', [Validators.required, Validators.min(3)]],
        password: ['', [Validators.required, Validators.pattern(StrongPasswordRegx)]],
    });
    public form: FormGroup = this.fb.group({
        email: ['', [Validators.required, Validators.email]],
        username: ['', [Validators.required, Validators.min(3)]],
        password: ['', [Validators.required, Validators.pattern(StrongPasswordRegx)]],
    });

    get passwordFormField() {
        if (this.form.get('password') === null) {
            return null;
        }
        return this.form.get('password');
    }
    get passwordFormField() {
        if (this.form.get('password') === null) {
            return null;
        }
        return this.form.get('password');
    }

    /**
     * Subscription au service d'authentification
     * @type {Subscription}
     * @memberof RegisterComponent
     * @private
     *
     */
    private authSubscription: Subscription = new Subscription();

    constructor(
        private authService: AuthService,
        private fb: FormBuilder,
        private router: Router,
        private sessionService: SessionService
    ) { }

    /**
     * Désinscrit de tous les observables
     * @memberof RegisterComponent
     * @public
     *
     */
    ngOnDestroy(): void {
        this.authSubscription.unsubscribe();
    }

    /**
     * Envoie le formulaire d'inscription
     * @memberof RegisterComponent
     * @public
     *
     */
    public submit(): void {
        const registerRequest = this.form.value as RegisterRequest;
        this.authSubscription.add(
            this.authService.register(registerRequest).subscribe({
                next: () => {
                    this.sessionService.logIn();
                    this.router.navigate(['/article']);
                },
                error: (error: HttpErrorResponse) => {
                    this.onError = true;
                    this.errorMessage = error.error.message;
                }
            })
        )
    }

}
