import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {User} from '../models/user.interface';
import {CookieService} from "ngx-cookie-service";

@Injectable({
   providedIn: 'root',
})
/**
 * Service pour la session utilisateur
 */
export class SessionService {
   public isLogged = false;
   public user: User | undefined;

   private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

   constructor(private cookieService: CookieService) {}

   /**
    * Observable pour savoir si l'utilisateur est connecté
    * @returns {Observable<boolean>}
    */
   public $isLogged(): Observable<boolean> {
      return this.isLoggedSubject.asObservable();
   }

   public logIn(user: User): void {
      this.user = user;
      this.isLogged = true;
      this.next();
   }

   public autoLogIn(): void {
      const token = this.cookieService.get('token');
      if (token) {
         this.isLogged = true;
         this.next();
      }
   }

   public logOut(): void {
      this.cookieService.delete('token');
      this.user = undefined;
      this.isLogged = false;
      this.next();
   }

   private next(): void {
      this.isLoggedSubject.next(this.isLogged)
   }
}
