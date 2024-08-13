import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import {SessionService} from "../../../../core/services/session.service";
import {ArticleService} from "../../../../core/services/article.service";
import {Observable} from "rxjs";
import {Article} from "../../../../core/models/article.interface";

@Component({
   selector: 'app-article',
   templateUrl: './list-article.component.html',
   styleUrls: ['./list-article.component.scss']
})
export class ListArticleComponent implements OnInit {
   public sortValue: string = 'none';
   private articles$: Observable<Article[]> = this.articleService.getAll();
   private articles: any = [];

   constructor(
       private sessionService: SessionService,
       private matSnackBar: MatSnackBar,
       private articleService: ArticleService
   ) {
   }

   ngOnInit(): void {
      this.articles$.subscribe((response) => {
         this.articles = response;
      });
   }

   public getArticles(): Article[] {
      return this.articles;
   }

   public sortArticles(): void {
      this.articles = this.articles.sort((a: Article, b: Article) => {
         switch (this.sortValue) {
            case 'date-asc':
               return new Date(b.date).getTime() - new Date(a.date).getTime();
            case 'date-desc':
               return new Date(a.date).getTime() - new Date(b.date).getTime();
            case 'title-asc':
               return a.title.localeCompare(b.title);
            case 'title-desc':
               return b.title.localeCompare(a.title);
            default:
               return 0;
         }
      });
   }
}
