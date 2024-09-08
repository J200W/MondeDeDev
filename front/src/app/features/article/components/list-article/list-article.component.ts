import { Component, OnDestroy, OnInit } from '@angular/core';
import { ArticleService } from "../../../../core/services/article.service";
import { Observable, Subscription } from "rxjs";
import { Article } from "../../../../core/models/article.interface";

@Component({
    selector: 'app-article',
    templateUrl: './list-article.component.html',
    styleUrls: ['./list-article.component.scss']
})
/**
 * Composant de liste des articles
 * @class
 * @implements {OnInit}
 * @implements {OnDestroy}
 * @public
 */
export class ListArticleComponent implements OnInit, OnDestroy {
    /**
     * Valeur de tri
     * @type {string}
     */
    public sortValue: string = 'none';
    /** 
     * Observable des articles
     * @type {Observable<Article[]>}
     */
    private articles$: Observable<Article[]> = this.articleService.getAll();
    /**
     * Liste des articles
     * @type {Article[]}
     */
    private articles: Article[] = [];
    /**
     * Subscription au service d'article
     * @type {Subscription}
     */
    private articleSubscription: Subscription = new Subscription();

    constructor(
        private articleService: ArticleService
    ) {
    }

    ngOnInit(): void {
        this.articleSubscription.add(this.articles$.subscribe((response) => {
            this.articles = response;
        }));
    }

    ngOnDestroy(): void {
        this.articleSubscription.unsubscribe();
    }

    /**
     * Récupère les articles
     * @returns {Article[]}
     */
    public getArticles(): Article[] {
        return this.articles;
    }

    /**
     * Trie les articles
     */
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
