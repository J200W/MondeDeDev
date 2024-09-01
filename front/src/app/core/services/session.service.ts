import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../models/user.interface';
import { CookieService } from "ngx-cookie-service";
import { AuthService } from 'src/app/features/authentification/service/auth.service';

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

    constructor(private cookieService: CookieService, private authService: AuthService) { }

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
            this.authService.me().subscribe({
                next: (user: User) => {
                    this.user = user;
                    this.isLogged = true;
                    this.next();
                },
                error: (_) => {
                    this.isLogged = false;
                    this.logOut();
                }
            });
        }
    }


    public async logOut(): Promise<void> {
        this.user = undefined;
        this.isLogged = false;
        this.cookieService.delete('token');
        this.next();
    }

    private next(): void {
        this.isLoggedSubject.next(this.isLogged)
    }
}
