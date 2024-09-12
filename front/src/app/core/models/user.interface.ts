/**
 * Interface pour les utilisateurs
 * @interface
 */
export interface User {
   username: string,
   email: string,
   password: string,
   roles: string[],
}
