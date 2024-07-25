import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {User} from 'src/app/interfaces/user.interface';
import {SessionService} from "../../services/session.service";

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
   private pathService: string = 'http://localhost:8080/api/subscription';

   constructor(private httpClient: HttpClient) {
   }

   /**
    * Souscrire à un thème
    * @returns {Observable<void>}
    * @memberof ThemeService
    * @public
    * @param topicId
    * @param userId
    */
   public subscribe(topicId: number, userId: number | undefined): Observable<void> {
      return this.httpClient.post<any>(`${this.pathService}/sub`,
          {
             topic: {
                id: topicId
             },
             user: {
                id: userId
             }
          }
      );
   }
}
