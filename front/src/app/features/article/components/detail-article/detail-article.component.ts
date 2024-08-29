import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ArticleService } from "../../../../core/services/article.service";
import { SessionService } from "../../../../core/services/session.service";
import { Comment } from "../../../../core/models/comment.interface";
import { CommentService } from "../../../../core/services/comment.service";
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-detail-article',
    templateUrl: './detail-article.component.html',
    styleUrls: ['./detail-article.component.scss']
})
export class DetailArticleComponent implements OnInit, OnDestroy {

    public canModify: boolean = false;
    public article: any;
    public comments: Comment[] = [];
    public commentForm: FormGroup = this.fb.group({
        user: [''],
        post: [''],
        content: ['', [Validators.required, Validators.min(2)]]
    });
    private id: string | undefined;
    private commentSubscription!: Subscription;
    private commentSubscription2!: Subscription;

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
        this.articleService.getPost(this.id).subscribe((article) => {
            this.article = article;
            this.canModify = this.sessionService.user!.id === article.user.id;
            this.commentForm = this.fb.group({
                user: [this.sessionService.user!.id],
                post: [article.id],
                content: ['', [Validators.required, Validators.min(2)]]
            })
            this.commentSubscription = this.commentService.getAllComments(article.id).subscribe((comments) => {
                this.comments = comments;
            });
        }).add(() => {
            if (this.article == null) {
                this.router.navigate(['/404']);
            }
        });
    }

    ngOnDestroy(): void {
        if (this.commentSubscription) {
            this.commentSubscription.unsubscribe();
        }
        if (this.commentSubscription2) {
            this.commentSubscription2.unsubscribe();
        }
    }

    public submitComment(): void {
        let formValue = {
            user: {
                id: this.commentForm.get('user')?.value
            },
            post: {
                id: this.commentForm.get('post')?.value
            },
            content: this.commentForm.get('content')?.value
        }
        this.commentSubscription2 = this.commentService.createComment(formValue).subscribe({
            next: () => {
                this.matSnackBar.open('Commentaire ajouté', 'Fermer', {
                    duration: 500
                }).afterDismissed().subscribe(() => {
                    this.commentForm.reset();
                    window.location.reload();
                });
            },
            error: () => {
                this.matSnackBar.open('Erreur lors de l\'ajout du commentaire', 'Fermer', {
                    duration: 500
                });
            },
            complete: () => {
                this.commentSubscription2.unsubscribe();
            }
        });
    }

}
