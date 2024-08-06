import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListArticleComponent} from './components/list-article/list-article.component';
import {FormArticleComponent} from './components/form-article/form-article.component';
import {DetailArticleComponent} from "./components/detail-article/detail-article.component";

const routes: Routes = [
   {title: 'Articles | MondeDeDév', path: '', component: ListArticleComponent},
   {title: 'Article | MondeDeDév', path: 'detail/:id', component: DetailArticleComponent},
   {title: 'Modifier un article | MondeDeDév', path: 'update/:id', component: FormArticleComponent},
   {title: 'Créer un article | MondeDeDév', path: 'create', component: FormArticleComponent},
];

@NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule]
})
export class ArticleRoutingModule {
}
