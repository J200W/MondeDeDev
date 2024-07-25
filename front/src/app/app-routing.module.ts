import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from "./guards/auth.guard";
import {UnauthGuard} from "./guards/unauth.guard";
import {MeComponent} from "./pages/components/me/me.component";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {ListArticleComponent} from "./pages/articles/components/list-article/list-article.component";
import {ThemeComponent} from "./pages/theme/theme.component";

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
   {
      path: 'article',
      canActivate: [AuthGuard],
      loadChildren: () => import('./pages/articles/article.module').then(m => m.ArticleModule)
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
      loadChildren: () => import('./pages/auth/auth.module').then(m => m.AuthModule)
   },
   {
      path: 'me',
      canActivate: [AuthGuard],
      title: 'Mon compte | MondeDeDév',
      component: MeComponent
   },
   { path: '404', component: NotFoundComponent, title: '404 | MondeDeDév' },
   { path: '**', redirectTo: '/404' }
];

@NgModule({
   imports: [RouterModule.forRoot(routes)],
   exports: [RouterModule],
})
export class AppRoutingModule {
}
