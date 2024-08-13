import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {RouterModule} from '@angular/router';
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {HeaderComponent} from "./layout/header/header.component";
import {FooterComponent} from "./layout/footer/footer.component";
import {MaterialModule} from "./material.module";
import {CookieService} from "ngx-cookie-service";

@NgModule({
   declarations: [
      HeaderComponent,
      FooterComponent
   ],
   imports: [
      CommonModule,
      HttpClientModule,
      RouterModule,
      MaterialModule
   ],
   providers: [
      {
         provide: HTTP_INTERCEPTORS, useClass:
         JwtInterceptor, multi: true
      },
      CookieService
   ],
   exports: [
      MaterialModule,
      HeaderComponent,
      FooterComponent
   ]
})
export class CoreModule {
}
