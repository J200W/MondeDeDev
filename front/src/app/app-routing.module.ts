import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from "./core/guards/auth.guard";
import {UnauthGuard} from "./core/guards/unauth.guard";
import {MeComponent} from "./features/my-account/me/me.component";
import {NotFoundComponent} from "./features/not-found/not-found.component";
import {ThemeComponent} from "./features/theme/theme.component";

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
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
export class AppRoutingModule {
}
