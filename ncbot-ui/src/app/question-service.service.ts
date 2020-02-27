import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Question} from "./question";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class QuestionServiceService {
  host = "http://localhost:8080/";
  question:Question;
  constructor(private httpClient: HttpClient) {
  }

  getAllQuestions(): Observable<any> {
    let url = this.host + "questions/all/text";
    return this.httpClient.get(url);
  }

  sendQuestion(question:Question):any {
    let url = this.host + "questions/add";
    return this.httpClient.post(url, question);
  }
}
