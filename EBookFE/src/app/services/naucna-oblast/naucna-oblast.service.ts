import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NaucnaOblastService {

  private BASE_URL = 'http://localhost:8080/newController';
  private OLGA = 'http://localhost:8080/noCasopisController';

  constructor(private httpClient: HttpClient, private http : Http) {
  }

  novaNO(id: any){
    return this.httpClient.get(`${this.BASE_URL}/novaNO/${id}`) as Observable<any> ;
  }

  posaljiNO(no, taskId) {
    return this.httpClient.post("http://localhost:8080/newController/posaljiNO/".concat(taskId), no) as Observable<any>;
  }

  getEmail(id: any){
    return this.httpClient.get(`${this.BASE_URL}/getEmail/${id}`) as Observable<any> ;
  }

  posaljiEmailKraj(no, taskId) {
    return this.httpClient.post("http://localhost:8080/newController/posaljiEmailKraj/".concat(taskId), no) as Observable<any>;
  }

  // casopis
  novaNOCasopis(id: any){
    return this.httpClient.get(`${this.OLGA}/novaNO/${id}`) as Observable<any> ;
  }

  posaljiNOCasopis(no, taskId) {
    return this.httpClient.post("http://localhost:8080/noCasopisController/posaljiNO/".concat(taskId), no) as Observable<any>;
  }

  sledeciTaskCasopis(processId: string) {
    return this.httpClient.get('http://localhost:8080/casopis/sledeciTaskCasopis/'.concat(processId)) as Observable<any>;
  }

  updateCasopis(magazine, taskId) {
    console.log('update magazine');
    console.log(magazine);
    return this.httpClient.post('http://localhost:8080/casopis/update/'.concat(taskId), magazine) as Observable<any>;
  }






}
