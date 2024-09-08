import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ArticleService } from "../../../../core/services/article.service";
import { SessionService } from "../../../../core/services/session.service";
import { Comment } from "../../../../core/models/comment.interface";
import { CommentService } from "../../../../core/services/comment.service";
import { Article } from 'src/app/core/models/article.interface';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-detail-article',
    templateUrl: './detail-article.component.html',
    styleUrls: ['./detail-article.component.scss']
})
/**
 * Composant de détail d'un article
 * @class
 * @implements {OnInit}
 * @implements {OnDestroy}
 * @public
 */
export class DetailArticleComponent implements OnInit, OnDestroy {

    /**
     * Article
     * @type {Article}
     */
    public article: Article | undefined;
    /**
     * Liste des commentaires
     * @type {Comment[]}
     */
    public comments: Comment[] = [];
    /**
     * Formulaire de commentaire
     * @type {FormGroup}
     */
    public commentForm: FormGroup = this.fb.group({
        user: [''],
        post: [''],
        content: ['', [Validators.required, Validators.min(2)]]
    });
    /**
     * Id de l'article
     * @type {string | undefined}
     */
    private id: string | undefined;
    /**
     * Subscription au service d'article
     * @type {Subscription}
     */
    private articleSubscription: Subscription = new Subscription();

    constructor(
        private route: ActivatedRoute,
        private matSnackBar: MatSnackBar,
        private fb: FormBuilder,
        private articleService: ArticleService,
        private sessionService: SessionService,
        private commentService: CommentService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.id = this.route.snapshot.paramMap.get('id')!;
        this.articleSubscription.add(this.articleService.getPost(this.id).subscribe((article) => {
            this.article = article;
            this.commentForm = this.fb.group({
                user: [this.sessionService.user!.id],
                post: [article.id],
                content: ['', [Validators.required, Validators.min(2)]]
            })
            this.articleSubscription.add(this.commentService.getAllComments(this.id!).subscribe((comments) => {
                this.comments = comments;
            }));
        }).add(() => {
            if (this.article == null) {
                this.router.navigate(['/404']);
            }
        }));
    }

    ngOnDestroy(): void {
        this.articleSubscription.unsubscribe();
    }

    /**
     * Soumettre le commentaire
     */
    public submitComment(): void {
        let formValue: Comment = {
            user: {
                id: this.sessionService.user!.id,
                username: this.sessionService.user!.username
            },
            post: {
                id: this.id!,
            },
            content: this.commentForm.get('content')?.value,
            id: '',
            date: new Date()
        };
        this.articleSubscription.add(this.commentService.createComment(formValue).subscribe(() => {
            this.matSnackBar.open('Commentaire ajouté', 'Fermer', {
                duration: 500
            }).afterDismissed().subscribe(() => {
                this.commentForm.reset();
                window.location.reload();
            });
        }));
    }
}
