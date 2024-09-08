import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from "@angular/flex-layout";
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './features/home/home.component';
import {MeComponent} from './features/my-account/me/me.component';
import {NotFoundComponent} from "./features/not-found/not-found.component";
import {ThemeComponent} from './features/theme/theme.component';
import {CoreModule} from "./core/core.module";
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
   declarations: [AppComponent, HomeComponent, NotFoundComponent, MeComponent, ThemeComponent],
   imports: [
      BrowserModule,
      AppRoutingModule,
      FlexLayoutModule,
      BrowserAnimationsModule,
      CoreModule,
      ReactiveFormsModule,
   ],
   bootstrap: [AppComponent],
})
/**
 * Module principal
 * @class
 */
export class AppModule {}
