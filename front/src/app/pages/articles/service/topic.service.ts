import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Topic} from "../interfaces/topic.interface";
import {Article} from "../interfaces/article.interface";

@Injectable({
   providedIn: 'root',
})
export class TopicService {

   /**
    * Chemin vers le service
    * @type {string}
    * @default api/auth
    * @private
    */
   private pathService: string = 'http://localhost:8080/api/topic';

   constructor(private httpClient: HttpClient) {
   }

   /**
    * Obtenir tous les thèmes
    * @returns {Observable<Topic[]>}
    * @public
    */
   public getAllTopics(): Observable<Topic[]> {
      return this.httpClient.get<Topic[]>(`${this.pathService}/all`);
   }
}