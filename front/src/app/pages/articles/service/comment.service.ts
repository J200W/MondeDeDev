import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Comment} from "../interfaces/comment.interface";
import {HttpClient} from "@angular/common/http";

@Injectable({
   providedIn: 'root',
})
export class CommentService {

   /**
    * Chemin vers le service
    * @type {string}
    * @default api/auth
    * @private
    */
   private pathService: string = 'http://localhost:8080/api/comment';

   constructor(private httpClient: HttpClient) {
   }

   /**
    * Obtenir tous les commentaires
    * @returns {Observable<Comment[]>}
    * @public
    */
   public getAllComments(postId: number): Observable<Comment[]> {
      return this.httpClient.get<Comment[]>(`${this.pathService}/all/${postId}`);
   }

   /**
    * Créer un commentaire
    * @param comment
    * @returns {Observable<Comment>}
    */
   public createComment(comment: any): Observable<Comment> {
      return this.httpClient.post<Comment>(`${this.pathService}/add`, comment);
   }
}