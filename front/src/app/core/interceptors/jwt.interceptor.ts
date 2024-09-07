import { HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
    constructor(
    ) {
    }

    public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        req = req.clone({
            withCredentials: true,
        });

        return next.handle(req);
    }
}

export const httpInterceptorProviders = [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
];