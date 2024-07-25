import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ArticleRoutingModule} from './article-routing.module';
import {ListArticleComponent} from './components/list-article/list-article.component';
import {FormArticleComponent} from './components/create-article/form-article.component';
import {DetailArticleComponent} from './components/detail-article/detail-article.component';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {MatSelectModule} from "@angular/material/select";

const materialModules = [
   MatButtonModule,
   MatCardModule,
   MatFormFieldModule,
   MatIconModule,
   MatInputModule,
   MatSelectModule,
]

@NgModule({
   declarations: [
      ListArticleComponent,
      FormArticleComponent,
      DetailArticleComponent
   ],
   imports: [
      ArticleRoutingModule,
      CommonModule,
      FormsModule,
      ReactiveFormsModule,
      ...materialModules
   ]
})
export class ArticleModule {
}
