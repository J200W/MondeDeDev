import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ArticleService} from "../../service/article.service";
import {SessionService} from "../../../../services/session.service";
import {TopicService} from "../../service/topic.service";
import {Comment} from "../../interfaces/comment.interface";
import {CommentService} from "../../service/comment.service";

@Component({
   selector: 'app-detail-article',
   templateUrl: './detail-article.component.html',
   styleUrls: ['./detail-article.component.scss']
})
export class DetailArticleComponent implements OnInit {

   public canModify: boolean = false;
   private id: string | undefined;
   public article: any;
   public comments: Comment[] = [];
   public commentForm: FormGroup = this.fb.group({});

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
         this.canModify = this.sessionService.user!.id === article.user.id;
         this.article = article;
         this.commentService.getAllComments(article.id).subscribe((comments) => {
            this.comments = comments;
            this.commentForm = this.fb.group({
               user: [this.sessionService.user!.id],
               post: [article.id],
               content: ['', [Validators.required, Validators.min(2)]]
            })
         });
      });
   }

   public submitComment(): void {
      this.commentService.createComment(this.commentForm.value).subscribe((comment) => {
         this.comments.push(comment);
         this.commentForm.reset();
      });
   }

}
