import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { User } from '../models/user.interface';
import { AuthService } from 'src/app/features/authentification/service/auth.service';

@Injectable({
    providedIn: 'root',
})
export class SessionService {
    /**
     * Indique si l'utilisateur est connecté
     * @type {BehaviorSubject<boolean>}
     */
    public isLoggedSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.getLocalStorageIsLogged());
    
    /**
     * Utilisateur connecté
     * @type {User | undefined}
     */
    public user: User | undefined;

    private sessionSubscription: Subscription = new Subscription();
    
    /**
     * Constructeur du service
     * @param authService 
     */
    constructor(private authService: AuthService) {
        this.autoLog();
    }

    /**
     * Observable de l'état de connexion (qui est un BehaviorSubject à l'initialisation)
     * @returns {Observable<boolean>}
     */
    public $isLogged(): Observable<boolean> {
        return this.isLoggedSubject.asObservable();
    }

    /**
     * Connecte l'utilisateur
     * @param user 
     */
    public logIn(): void {
        this.setLocalStorageIsLogged(true);
        this.isLoggedSubject.next(true);
    }

    /**
     * Déconnecte l'utilisateur
     */
    public logOut(): void {
        this.setLocalStorageIsLogged(false);
        this.isLoggedSubject.next(false);
    }

    /**
     * Vérifie si l'utilisateur est connecté
     */
    public autoLog(): void {
        this.sessionSubscription.add(this.authService.isLogged().subscribe({
            next: (isLogged: boolean) => {
                this.setLocalStorageIsLogged(isLogged);
                this.isLoggedSubject.next(isLogged);
                this.sessionSubscription.add(this.authService.me().subscribe({
                    next: (user: User) => {
                        this.user = user;
                    },
                    error: (_) => {
                        this.logOut();
                    },
                    complete: () => {
                        this.sessionSubscription.unsubscribe();
                    }
                }));
            },
            error: (_) => {
                this.logOut();
            },
        }));
    }

    /**
     * Enregistre l'état de connexion dans le localStorage
     * @param {boolean} isLogged
     */
    private setLocalStorageIsLogged(isLogged: boolean): void {
        localStorage.setItem('isLogged', JSON.stringify(isLogged));
    }

    /**
     * Récupère l'état de connexion dans le localStorage
     * @returns {boolean}
     */
    private getLocalStorageIsLogged(): boolean {
        const isLogged = localStorage.getItem('isLogged');
        return isLogged ? JSON.parse(isLogged) : false;
    }
}
