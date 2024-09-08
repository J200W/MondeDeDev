import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { SessionService } from "../services/session.service";
import { Observable } from "rxjs";
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
/**
 * Garde de route pour l'authentification
 * @class
 * @implements {CanActivate}
 */
export class AuthGuard implements CanActivate {

    constructor(
        private router: Router,
        private sessionService: SessionService,
    ) { }

    /**
     * Autorise l'accès à la route si l'utilisateur n'est pas connecté
     * @returns {Observable<boolean>}
     */
    public canActivate(): Observable<boolean> {
        return this.sessionService.$isLogged().pipe(
            map((isLogged: boolean) => {
                if (!isLogged) {
                    this.router.navigate(['/']);
                    return false; // Accès interdit si non connecté
                }
                return true; // Accès autorisé si connecté
            })
        );
    }
}
