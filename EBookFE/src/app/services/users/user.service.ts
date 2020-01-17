import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  fetchUsers() {
    return this.httpClient.get("http://localhost:8080/user/fetch") as Observable<any>;
  }

  registerUser(user, taskId) {
    return this.httpClient.post("http://localhost:8080/welcome/post/".concat(taskId), user) as Observable<any>;
  }

  // CASOPIS

  registerCasopis(casopis, taskId) {
    return this.httpClient.post("http://localhost:8080/kreiranjeCasopisa/post/".concat(taskId), casopis) as Observable<any>;
  }

  getAllRecenzenti() {
    return this.httpClient.get('http://localhost:8080/casopis/getAllRecenzenti') as Observable<any>;
  }
  getAllUrednici() {
    return this.httpClient.get('http://localhost:8080/casopis/getAllUrednici') as Observable<any>;
  }
}
