import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SubscriptionInterface } from "../models/subscription.interface";
import { ResponseStatus } from 'src/app/features/authentification/interfaces/responseSuccess.interface';
import {environment} from "../../../environments/environment";

@Injectable({
    providedIn: 'root',
})
export class SubscriptionService {
    /**
     * Chemin vers le service
     * @type {string}
     * @memberof AuthService
     * @default api/auth
     * @private
     */
    private pathService: string = `${environment.apiBaseUrl}/api/subscription`;

    constructor(private httpClient: HttpClient) {
    }

    /**
     * Souscrire à un thème
     * @returns {Observable<ResponseStatus>}
     * @memberof ThemeService
     * @public
     * @param topicId
     * @param userId
     */
    public subscribe(topicId: number): Observable<ResponseStatus> {
        return this.httpClient.post<ResponseStatus>(`${this.pathService}/sub/${topicId}`, {});
    }

    /**
     * Désinscrire à un thème
     * @returns {Observable<ResponseStatus>}
     * @memberof ThemeService
     * @public
     * @param subscriptionId
     */
    public unsubscribe(subscriptionId: number): Observable<ResponseStatus> {
        console.log(subscriptionId);
        return this.httpClient.delete<ResponseStatus>(`${this.pathService}/sub/${subscriptionId}`);
    }

    /**
     * Obtenir les souscriptions
     * @returns {Observable<SubscriptionInterface[]>}
     * @memberof ThemeService
     * @public
     */
    public getSubscriptions(): Observable<SubscriptionInterface[]> {
        return this.httpClient.get<SubscriptionInterface[]>(`${this.pathService}/me`);
    }
}
