import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SessionService} from "../../../../core/services/session.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ArticleService} from "../../../../core/services/article.service";
import {Article} from "../../../../core/models/article.interface";
import {TopicService} from "../../../../core/services/topic.service";
import {Topic} from "../../../../core/models/topic.interface";

@Component({
   selector: 'app-create-article',
   templateUrl: './form-article.component.html',
   styleUrls: ['./form-article.component.scss']
})
export class FormArticleComponent implements OnInit {

   public articleForm: FormGroup = this.fb.group({
      topic: ['', [Validators.required]],
      title: ['', [Validators.required]],
      content: ['', [Validators.required]]
   });
   public selectedTopic: Topic[] = [];

   private id: string | undefined;

   constructor(
       private route: ActivatedRoute,
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
      this.topicService.getAll().subscribe((topics) => {
         this.selectedTopic = topics;
      });
   }

   public submit(): void {
      const articleDate = {
         title: this.articleForm!.get('title')?.value,
         content: this.articleForm!.get('content')?.value.replace(/\n/g, '<br>'),
         user: {
            id: this.sessionService.user!.id,
         },
         topic: {
            id: this.articleForm!.get('topic')?.value,
         }
      };

      this.articleService
      .create(articleDate)
      .subscribe(() => {
         this.router.navigate(['/article']);
         this.matSnackBar.open('Article créé', 'Fermer', {
            duration: 2000,
         });
      });
   }

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
