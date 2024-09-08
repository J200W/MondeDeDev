/**
 * Interface pour les articles
 * @interface
 */
export interface Article {
   id: number;
   title: string;
   content: string;
   date: Date;
   user: {
      id: number,
      email: string,
      username: string,
   },
   topic: {
      id: number
      title: string,
      description: string
   }
}