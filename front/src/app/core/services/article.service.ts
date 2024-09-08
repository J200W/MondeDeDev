import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Article} from "../models/article.interface";
import {environment} from "../../../environments/environment";

@Injectable({
   providedIn: 'root',
})
export class ArticleService {

   /**
    * Chemin vers le service
    * @type {string}
    * @default api/auth
    * @private
    */
   private pathService: string = `${environment.apiBaseUrl}/api/post`;

   /**
    * Constructeur du service
    * @param httpClient 
    */
   constructor(private httpClient: HttpClient) {
   }

   /**
    * Obtenir un article
    * @returns {Observable<Article>}
    * @param id
    * @public
    */
   public getPost(id: string): Observable<Article> {
      return this.httpClient.get<Article>(`${this.pathService}/${id}`);
   }

   /**
    * Obtenir tous les articles
    * @returns {Observable<Article[]>}
    * @public
    */
   public getAll(): Observable<Article[]> {
      return this.httpClient.get<Article[]>(`${this.pathService}/all`);
   }

   /**
    * Créer un article
    * @param article
    * @returns {Observable<Article>}
    */
   public create(article: Article): Observable<Article> {
      return this.httpClient.post<Article>(`${this.pathService}/create`, article);
   }
}