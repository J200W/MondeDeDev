import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from "../models/topic.interface";
import { environment } from "src/app/environments/environment";

@Injectable({
    providedIn: 'root',
})
/**
 * Service pour les thèmes
 * @class
 */
export class TopicService {
    /**
     * Chemin vers le service
     * @type {string}
     * @memberof AuthService
     * @default api/auth
     * @private
     */
    private pathService: string = `${environment.apiBaseUrl}/api/topic`;

    constructor(private httpClient: HttpClient) { }

    /**
     * Obtenir tous les thèmes
     * @returns {Observable<Topic[]>}
     * @public
     */
    public getAll(): Observable<Topic[]> {
        return this.httpClient.get<Topic[]>(`${this.pathService}/all`);
    }

    /**
     * Obtenir les thèmes non souscrits
     * @returns {Observable<Topic[]>}
     * @public
     */
    public getNotSubscribed(): Observable<Topic[]> {
        return this.httpClient.get<Topic[]>(`${this.pathService}/not-subscribed`);
    }
}
