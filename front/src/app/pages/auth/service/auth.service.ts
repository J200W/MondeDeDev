import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { AuthSuccess } from '../interfaces/authSuccess.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { User } from 'src/app/interfaces/user.interface';

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
    private pathService: string = 'http://localhost:8080/api/auth';

    constructor(private httpClient: HttpClient) {}

    /**
     * Enregistre un utilisateur
     * @param {RegisterRequest} registerRequest
     * @return {Observable<AuthSuccess>}
     * @memberof AuthService
     * @public
     */

    public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
        console.log('registerRequest', registerRequest);
        return this.httpClient.post<AuthSuccess>(
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

    public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
        return this.httpClient.post<AuthSuccess>(
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
        return this.httpClient.get<User>(`${this.pathService}/me`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        });
    }
}
