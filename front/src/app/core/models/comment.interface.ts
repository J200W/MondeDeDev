/**
 * Interface pour les commentaires
 * @interface
 */
export interface Comment {
   id: string;
   content: string;
   user: {
      id: number;
      username: string;
   };
   post: {
      id: string;
   };
   date: Date;
}