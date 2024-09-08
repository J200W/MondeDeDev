import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { ResponseStatus } from '../interfaces/responseSuccess.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { User } from 'src/app/core/models/user.interface';
import { environment } from 'src/environments/environment'; 

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    /**
     * Chemin vers le service
     * @type {string}
     * @memberof AuthService
     * @default api/auth
     * @private
     */
    private pathService: string = `${environment.apiBaseUrl}/api/auth`;

    constructor(private httpClient: HttpClient) {
    }

    /**
     * Enregistre un utilisateur
     * @param {RegisterRequest} registerRequest
     * @return {Observable<ResponseStatus>}
     * @memberof AuthService
     * @public
     */

    public register(registerRequest: RegisterRequest): Observable<ResponseStatus> {
        console.log('registerRequest', registerRequest);
        return this.httpClient.post<ResponseStatus>(
            `${this.pathService}/register`,
            registerRequest
        );
    }

    /**
     * Connecte un utilisateur
     * @param {LoginRequest} loginRequest
     * @returns {Observable<AuthSuccess>}
     * @memberof AuthService
     * @public
     */

    public login(loginRequest: LoginRequest): Observable<ResponseStatus> {
        return this.httpClient.post<ResponseStatus>(
            `${this.pathService}/login`,
            loginRequest
        );
    }

    /**
     * Récupère l'utilisateur connecté
     * @returns {Observable<User>}
     * @memberof AuthService
     * @public
     */

    public me(): Observable<User> {
        return this.httpClient.get<User>(`${this.pathService}/me`);
    }

    /**
     * Met à jour l'utilisateur connecté
     * @param {User} user
     * @returns {Observable<void>}
     */
    public update(user: User): Observable<void> {
        return this.httpClient.put<void>(`${this.pathService}/me`, user);
     }

    /**
     * Déconnecte l'utilisateur
     * @returns {Observable<void>}
     * @memberof AuthService
     * @public
     */
    public logout(): Observable<void> {
        return this.httpClient.get<void>(`${this.pathService}/logout`);
    }

    /**
     * Vérifie si l'utilisateur est connecté
     * @returns {Observable<User>}
     * @memberof AuthService
     * @public
     */
    public isLogged(): Observable<boolean> {
        return this.httpClient.get<boolean>(`${this.pathService}/is-logged`);
    }
}
