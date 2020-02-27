import {Component, OnInit} from '@angular/core';
import {Question} from "../question";
import {QuestionServiceService} from "../question-service.service";
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {ModalAddQuestionComponent} from "../modal-add-question/modal-add-question.component";

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css']
})
export class QuestionsComponent implements OnInit {
  questions:Question[];
  constructor(private questionService: QuestionServiceService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.questionService.getAllQuestions().subscribe((result:Question[]) =>
      {
        this.questions = result;
      }
    );
  }

  openCreationNewQuestionForm(){
    this.modalService.open(ModalAddQuestionComponent)
  }
}
