import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { SessionService } from "../../../../core/services/session.service";
import { MatSnackBar } from "@angular/material/snack-bar";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ArticleService } from "../../../../core/services/article.service";
import { Article } from "../../../../core/models/article.interface";
import { TopicService } from "../../../../core/services/topic.service";
import { Topic } from "../../../../core/models/topic.interface";
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-create-article',
    templateUrl: './form-article.component.html',
    styleUrls: ['./form-article.component.scss']
})
/**
 * Composant de création d'un article
 * @class
 * @implements {OnInit}
 * @implements {OnDestroy}
 * @public
 */
export class FormArticleComponent implements OnInit, OnDestroy {

    /**
     * Formulaire d'article
     * @type {FormGroup}
     */
    public articleForm: FormGroup = this.fb.group({
        topic: ['', [Validators.required]],
        title: ['', [Validators.required]],
        content: ['', [Validators.required]]
    });
    /**
     * Liste des topics
     * @type {Topic[]}
     */
    public selectedTopic: Topic[] = [];
    /**
     * Subscription au service d'article
     * @type {Subscription}
     */
    private articleSubscription: Subscription = new Subscription();

    /**
     * Constructeur du composant
     * @param fb 
     * @param matSnackBar 
     * @param articleService 
     * @param sessionService 
     * @param topicService 
     * @param router 
     */
    constructor(
        private fb: FormBuilder,
        private matSnackBar: MatSnackBar,
        private articleService: ArticleService,
        private sessionService: SessionService,
        private topicService: TopicService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        const url = this.router.url;
        this.initForm();
        this.articleSubscription.add(this.topicService.getAll().subscribe((topics) => {
            this.selectedTopic = topics;
        }));
        console.log(this.sessionService.user);
    }

    ngOnDestroy(): void {
        this.articleSubscription.unsubscribe();
    }

    /**
     * Soumettre le formulaire
     */
    public submit(): void {
        const articleDate: Article = {
            id: 0,
            date: new Date(),
            title: this.articleForm!.get('title')?.value,
            content: this.articleForm!.get('content')?.value.replace(/\n/g, '<br>'),
            user: {
                id: this.sessionService.user!.id,
                email: this.sessionService.user!.email,
                username: this.sessionService.user!.username,
            },
            topic: {
                id: this.articleForm!.get('topic')?.value,
                description: '',
                title: ''
            }
        };

        this.articleSubscription.add(this.articleService
            .create(articleDate)
            .subscribe(() => {
                this.router.navigate(['/article']);
                this.matSnackBar.open('Article créé', 'Fermer', {
                    duration: 2000,
                });
            }));
    }

    /**
     * Initialisation du formulaire
     * @param article 
     */
    private initForm(article?: Article): void {
        if (
            article !== undefined &&
            article?.user?.id == this.sessionService.user!.id
        ) {
            this.articleForm = this.fb.group({
                topic: [article ? article.topic.id : '', [Validators.required]],
                title: [article ? article.title : '', [Validators.required]],
                content: [
                    article ? article.content : '',
                    [Validators.required, Validators.maxLength(2000)],
                ],
            });
        }
    }
}
