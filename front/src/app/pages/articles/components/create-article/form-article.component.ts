import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SessionService} from "../../../../services/session.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ArticleService} from "../../service/article.service";
import {Article} from "../../interfaces/article.interface";
import {TopicService} from "../../service/topic.service";
import {Topic} from "../../interfaces/topic.interface";

@Component({
   selector: 'app-create-article',
   templateUrl: './form-article.component.html',
   styleUrls: ['./form-article.component.scss']
})
export class FormArticleComponent implements OnInit {

   public onUpdate: boolean = false;
   public articleForm: FormGroup = this.fb.group({
      topic: ['', [Validators.required]],
      title: ['', [Validators.required]],
      content: ['', [Validators.required, Validators.maxLength(2000)]]
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
      if (url.includes('update')) {
         this.onUpdate = true;
         this.id = this.route.snapshot.paramMap.get('id')!;

         this.articleService
             .getPost(this.id)
             .subscribe((article: Article) => {
                this.initForm(article);
             });
      } else {
         this.initForm();
      }
      this.topicService.getAllTopics().subscribe((topics) => {
         this.selectedTopic = topics;
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

   public submit(): void {
      const articleDate = {
         title: this.articleForm!.get('title')?.value,
         content: this.articleForm!.get('content')?.value,
         user: {
            id: this.sessionService.user!.id,
         },
         topic: {
            id: this.articleForm!.get('topic')?.value,
         }
      };

      if (this.onUpdate) {
         this.articleService.updatePost(articleDate).subscribe(() => {
            this.router.navigate(['/article']);
            this.matSnackBar.open('Article modifié', 'Fermer', {
               duration: 2000,
            });
         });
      } else {
         this.articleService
             .createPost(articleDate)
             .subscribe(() => {
                this.router.navigate(['/article']);
                this.matSnackBar.open('Article créé', 'Fermer', {
                   duration: 2000,
                });
             });
      }
   }
}
