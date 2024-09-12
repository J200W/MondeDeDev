/**
 * Interface pour les abonnements
 * @interface
 */
export interface SubscriptionInterface {
    id: number;
    user: {
        id: number;
        name: string;
        email: string;
    }
    topic: {
        url: string;
        title: string;
        description: string;
    }
}