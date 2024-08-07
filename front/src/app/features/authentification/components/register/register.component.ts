import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {pipe, Subscription} from "rxjs";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {SessionService} from "../../../../core/services/session.service";
import {RegisterRequest} from "../../interfaces/registerRequest.interface";
import {AuthSuccess} from "../../interfaces/authSuccess.interface";
import {User} from "../../../../core/models/user.interface";
import {StrongPasswordRegx} from "../../../../core/constants/strong-password-regex";

@Component({
   selector: 'app-register',
   templateUrl: './register.component.html',
   styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

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
   ) {}

   ngOnInit(): void {
   }

   /**
    * Envoie le formulaire d'inscription
    * @memberof RegisterComponent
    * @public
    *
    */

   public submit(): void {
      const registerRequest = this.form.value as RegisterRequest;
      this.authSubscription = this.authService
          .register(registerRequest)
          .subscribe(
              pipe(
                  (response: AuthSuccess) => {
                     localStorage.setItem('token', response.accessToken);
                     this.authService.me().subscribe((user: User) => {
                        this.sessionService.logIn(user);
                        this.router.navigate(['/article']);
                        this.authSubscription.unsubscribe();
                     });
                  },
                  (error) => {
                     this.onError = true;
                     this.authSubscri