/**
 * Interface pour les articles
 * @interface
 */
export interface Article {
   title: string;
   url: string;
   content: string;
   date: Date;
   user: {
      email: string,
      username: string,
   },
   topic: {
      url: string,
      title: string,
      description: string
   }
}