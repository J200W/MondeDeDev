/**
 * Interface pour les commentaires
 * @interface
 */
export interface Comment {
   content: string;
   user: {
      username: string;
   };
   post: {
      url: string;
   };
   date: Date;
}