import {HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {CookieService} from "ngx-cookie-service";

@Injectable({providedIn: 'root'})
export class JwtInterceptor implements HttpInterceptor {
   constructor(
       private cookieService: CookieService
   ) {
   }

   public intercept(request: HttpRequest<any>, next: HttpHandler) {
      const token = this.cookieService.get('token');
      if (token) {
         request = request.clone({
            setHeaders: {
               Authorization: `Bearer ${token}`,
            },
         });
      }
      return next.handle(request);
   }
}