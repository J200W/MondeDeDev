import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SubscriptionInterface } from "../models/subscription.interface";
import { ResponseAPI } from 'src/app/features/authentification/interfaces/responseApiSuccess.interface';
import {environment} from "../../../environments/environment";

@Injectable({
    providedIn: 'root',
})
/**
 * Service de souscription
 * @class
 */
export class SubscriptionService {
    /**
     * Chemin vers le service
     * @type {string}
     * @memberof AuthService
     * @private
     */
    private pathService: string = `${environment.apiBaseUrl}/api/subscription`;

    constructor(private httpClient: HttpClient) {
    }

    /**
     * Souscrire à un thème
     * @returns {Observable<ResponseAPI>}
     * @memberof ThemeService
     * @public
     * @param topicId
     */
    public subscribe(topicId: number): Observable<ResponseAPI> {
        return this.httpClient.post<ResponseAPI>(`${this.pathService}/sub/${topicId}`, {});
    }

    /**
     * Désinscrire à un thème
     * @returns {Observable<ResponseAPI>}
     * @memberof ThemeService
     * @public
     * @param subscriptionId
     */
    public unsubscribe(subscriptionId: number): Observable<ResponseAPI> {
        console.log(subscriptionId);
        return this.httpClient.delete<ResponseAPI>(`${this.pathService}/sub/${subscriptionId}`);
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
