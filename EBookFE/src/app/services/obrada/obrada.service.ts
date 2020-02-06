import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ObradaService {

  private BASE_URL = 'http://localhost:8080/obrada';

  constructor(private httpClient: HttpClient, private http : Http) {
  }

  startObradaProcess() {
    return this.httpClient.get('http://localhost:8080/obrada/startObradaProcess') as Observable<any>;
  }

  nastaviDaljeReg(no, taskId) {
    return this.httpClient.post("http://localhost:8080/obrada/nastaviDaljeReg/".concat(taskId), no) as Observable<any>;
  }

  sledeciTaskIzbor(processId: string) {
    return this.httpClient.get('http://localhost:8080/obrada/sledeciTaskIzbor/'.concat(processId)) as Observable<any>;
  }

  getAllCasopisi() {
    return this.httpClient.get('http://localhost:8080/obrada/getAllCasopisi') as Observable<any>;
  }

  sacuvajIzabranCasopis(casopis, taskId) {
    console.log(casopis);
    return this.httpClient.post('http://localhost:8080/obrada/sacuvajIzabranCasopis/'.concat(taskId), casopis) as Observable<any>;
  }








}
