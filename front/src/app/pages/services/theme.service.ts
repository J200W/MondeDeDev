import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ThemeInterface } from '../interfaces/theme.interface'
import { User } from 'src/app/interfaces/user.interface';

@Injectable({
    providedIn: 'root',
})
export class ThemeService {
    /**
     * Chemin vers le service
     * @type {string}
     * @memberof AuthService
     * @default api/auth
     * @private
     */
    private pathService: string = 'http://localhost:8080/api/topic';

    constructor(private httpClient: HttpClient) {}

    /**
     * Afficher les thèmes
     * @returns {Observable<ThemeInterface[]>}
     * @memberof ThemeService
     * @public
     */
      public getThemes(): Observable<ThemeInterface[]> {
         return this.httpClient.get<ThemeInterface[]>(`${this.pathService}/all`);
      }
}
