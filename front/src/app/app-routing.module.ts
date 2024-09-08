import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from "./core/guards/auth.guard";
import {UnauthGuard} from "./core/guards/unauth.guard";
import {MeComponent} from "./features/my-account/me/me.component";
import {NotFoundComponent} from "./features/not-found/not-found.component";
import {ThemeComponent} from "./features/theme/theme.component";

/**
 * Route de l'application
 * @type {Routes}
 */
const routes: Routes = [
   {
      path: 'article',
      canActivate: [AuthGuard],
      loadChildren: () => import('./features/article/article.module').then(m => m.ArticleModule)
   },
   {
      path: 'theme',
      canActivate: [AuthGuard],
      title: 'Theme | MondeDeDév',
      component: ThemeComponent
   },
   {
      path: '',
      canActivate: [UnauthGuard],
      loadChildren: () => import('./features/authentification/auth.module').then(m => m.AuthModule)
   },
   {
      path: 'me',
      canActivate: [AuthGuard],
      title: 'Mon compte | MondeDeDév',
      component: MeComponent
   },
   {path: '404', component: NotFoundComponent, title: '404 | MondeDeDév'},
   {path: '**', redirectTo: '/404'}
];

@NgModule({
   imports: [RouterModule.forRoot(routes)],
   exports: [RouterModule],
})
/**
 * Module de routage
 * @class
 */
export class AppRoutingModule {
}
