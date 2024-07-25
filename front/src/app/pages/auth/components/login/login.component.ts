import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {FormBuilder, Validators} from "@angular/forms";
import {SessionService} from "../../../../services/session.service";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {AuthSuccess} from "../../interfaces/authSuccess.interface";
import {LoginRequest} from "../../interfaces/loginRequest.interface";
import {User} from "../../../../interfaces/user.interface";

@Component({
   selector: 'app-login',
   templateUrl: './login.component.html',
   styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

   /**
    * Indique si le mot de passe est caché
    */
   public hide = true;

   /**
    * Indique si une erreur est survenue lors de la connexion
    */
   public onError = false;

   /**
    * Subscription au service d'authentification
    */
   private authSubscription: Subscription = new Subscription();

   /**
    * Formulaire de connexion
    */
   public form = this.fb.group({
      emailOrUsername: ['', [Validators.required ]],
      password: ['', [Validators.required, Validators.min(3)]],
   });

   constructor(private authService: AuthService,
               private fb: FormBuilder,
               private router: Router,
               private sessionService: SessionService) {
   }

   public submit(): void {
      const loginRequest = this.form.value as LoginRequest;
      this.authSubscription = this.authService.login(loginRequest).subscribe(
          (response: AuthSuccess) => {
             localStorage.setItem('token', response.accessToken);
             this.authService.me().subscribe((user: User) => {
                this.sessionService.logIn(user);
                this.router.navigate(['/article']);
                this.authSubscription.unsubscribe();
             });
             this.router.navigate(['/article']);
          },
          (error) => {
             this.onError = true;
             this.authSubscription.unsubscribe();
          }
      );
   }

   ngOnInit(): void {
   }

}
