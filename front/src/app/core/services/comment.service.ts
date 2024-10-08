import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Comment } from "../models/comment.interface";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/app/environments/environment";

@Injectable({
    providedIn: 'root',
})
/**
 * Service de commentaire
 * @class
 */
export class CommentService {

    /**
     * Chemin vers le service
     * @type {string}
     * @default api/comment
     * @private
     */
    private pathService: string = `${environment.apiBaseUrl}/api/comment`;

    constructor(private httpClient: HttpClient) {
    }

    /**
     * Obtenir tous les commentaires
     * @returns {Observable<Comment[]>}
     * @public
     */
    public getAllComments(postId: string): Observable<Comment[]> {
        return this.httpClient.get<Comment[]>(`${this.pathService}/all/${postId}`);
    }

    /**
     * Créer un commentaire
     * @param comment
     * @returns {Observable<Comment>}
     */
    public createComment(comment: Comment): Observable<Comment> {
        return this.httpClient.post<Comment>(`${this.pathService}/add`, comment);
    }
}