import {Article} from "../models/article.interface";

/**
 * Interface de réponse pour les articles
 */
export interface ArticleResponse {
   articles: Article[];
}