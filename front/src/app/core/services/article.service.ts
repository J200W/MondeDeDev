import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Article} from "../models/article.interface";

@Injectable({
   providedIn: 'root',
})
/**
 * Service pour les articles
 */
export class ArticleService {

   /**
    * Chemin vers le service
    * @type {string}
    * @default api/auth
    * @private
    */
   private pathService: string = 'http://localhost:8080/api/post';

   constructor(private httpClient: HttpClient) {
   }

   /**
    * Obtenir un article
    * @returns {Observable<Article>}
    * @public
    * @param id
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
   public create(article: any): Observable<Article> {
      return this.httpClient.post<Article>(`${this.pathService}/create`, article);
   }

   /**
    * Mettre à jour un article
    * @param articleId
    * @param article
    * @returns {Observable<Article>}
    */
   public update(article: any): Observable<Article> {
      return this.httpClient.post<Article>(`${this.pathService}/update`, article);
   }

}