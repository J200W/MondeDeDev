import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import {SessionService} from "../services/session.service";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

   constructor(
       private router: Router,
       private sessionService: SessionService,
   ) {
   }

   public canActivate(): boolean {
      this.sessionService.autoLogIn();
      if (!this.sessionService.isLogged) {
         this.sessionService.logOut();
         this.router.navigate(['/']);
         return false;
      }
      return true;
   }
}