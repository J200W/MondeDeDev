export interface SubscriptionInterface {
   id: number;
   user: {
      id: number;
      name: string;
      email: string;
   }
   topic: {
      id: number;
      title: string;
      description: string;
   }
}