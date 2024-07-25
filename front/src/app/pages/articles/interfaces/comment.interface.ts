export interface Comment {
      id: string;
      content: string;
      user: {
         id: string;
         username: string;
      };
      article: {
         id: string;
      };
      date: Date;
}