import {Component, OnInit} from '@angular/core';
import {NgbModal, NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Question} from "../question";
import {Option} from "../option";
import {QuestionServiceService} from "../question-service.service";


@Component({
  selector: 'app-modal-add-question',
  templateUrl: './modal-add-question.component.html',
  styleUrls: ['./modal-add-question.component.css']
})
export class ModalAddQuestionComponent implements OnInit {
  modalForm: NgbActiveModal;
  indexOfTrueOption:number;
  question: Question = {
    content: '',
    options: [],
    answer: ''
  };

  constructor(private modalService: NgbModal, private modal: NgbActiveModal, private questionService: QuestionServiceService) {
    this.modalForm = modal;
  }


  ngOnInit(): void {
  }

  saveQuestion() {
    let i:number = 0;
    for (let option of this.question.options){
      if (i == this.indexOfTrueOption) {
        this.question.answer = option.content;
      }
      i++;
    }
    this.questionService.sendQuestion(this.question).subscribe(result => {
        location.reload();
      },
      error => {
        alert("Error")
      });
    this.modalForm.close("Efrfrf");
  }

  addOptionToQuestion() {
    let opt: Option = new Option();
    this.question.options.push(opt);
  }

  removeOptionToQuestion() {

  }

}
