import {Component, OnChanges, OnInit} from '@angular/core';
import {map, Observable} from 'rxjs';
import { User } from './interfaces/user.interface';
import { AuthService } from './pages/auth/service/auth.service';
import { Router } from '@angular/router';
import { SessionService } from './services/session.service';

@Component({
   selector: 'app-root',
   templateUrl: './app.component.html',
   styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
   public isVisible: boolean = false;
   public isLogged$: Observable<boolean>;

   constructor(
       private authService: AuthService,
       public router: Router,
       private sessionService: SessionService
   ) {
      this.isLogged$ = this.sessionService.$isLogged();
   }

   ngOnInit(): void {
      this.autoLog();
   }

   public currentUrl(): string {
      return this.router.url;
   }

   public logout(): void {
      this.sessionService.logOut();
      this.router.navigate(['/']);
   }

   public autoLog(): void {
      this.authService.me().subscribe(
          (user: User) => {
             this.sessionService.logIn(user);
          },
          (_) => {
             this.sessionService.logOut();
          }
      );
   }
}
