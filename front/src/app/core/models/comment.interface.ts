/**
 * Interface pour les commentaires
 * @interface
 */
export interface Comment {
   id: string;
   content: string;
   user: {
      username: string;
   };
   post: {
      id: string;
   };
   date: Date;
}