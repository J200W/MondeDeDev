import {NgModule} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {MeComponent} from './pages/components/me/me.component';
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ThemeComponent} from './pages/theme/theme.component';
import { DetailArticleComponent } from './pages/articles/components/detail-article/detail-article.component';
import {MatSelectModule} from "@angular/material/select";

const materialModule = [
   MatButtonModule,
   MatCardModule,
   MatFormFieldModule,
   MatIconModule,
   MatInputModule,
   MatSnackBarModule,
   MatToolbarModule,
   MatSelectModule
]

@NgModule({
   declarations: [AppComponent, HomeComponent, NotFoundComponent, MeComponent, ThemeComponent ],
   imports: [
      HttpClientModule,
      BrowserModule,
      AppRoutingModule,
      FlexLayoutModule,
      BrowserAnimationsModule,
      ...materialModule
   ],
   providers: [
      {
         provide: HTTP_INTERCEPTORS, useClass:
         JwtInterceptor, multi: true
      },
   ],
   bootstrap: [AppComponent],
})
export class AppModule {
}
