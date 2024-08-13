import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {User} from '../models/user.interface';

@Injectable({
   providedIn: 'root',
})
export class UserService {
   private pathService = 'http://localhost:8080/api/auth';

   constructor(private httpClient: HttpClient) {
   }

   public getMe(): Observable<User> {
      return this.httpClient.get<User>(`${this.pathService}/me`);
   }

   public update(user: User): Observable<void> {
      return this.httpClient.put<void>(`${this.pathService}/me`, user);
   }
}
