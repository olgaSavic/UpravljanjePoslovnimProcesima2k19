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

  // -----------------------------------------------
  startObradaProcess() {
    return this.httpClient.get('http://localhost:8080/obrada/startObradaProcess') as Observable<any>;
  }

  nastaviDaljeReg(no, taskId) {
    return this.httpClient.post("http://localhost:8080/obrada/nastaviDaljeReg/".concat(taskId), no) as Observable<any>;
  }

  // ----------------------------------------------
  potvrdaNastavak(processId: string) {
    return this.httpClient.get('http://localhost:8080/obrada/potvrdaNastavak/'.concat(processId)) as Observable<any>;
  }

  sacuvajIzborNastavak(casopis, taskId) {
    console.log(casopis);
    return this.httpClient.post('http://localhost:8080/obrada/sacuvajIzborNastavak/'.concat(taskId), casopis) as Observable<any>;
  }

  // -----------------------------------------------
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

  // -----------------------------------------------
  unosInfoRad(processId: string) {
    return this.httpClient.get('http://localhost:8080/obrada/unosInfoRad/'.concat(processId)) as Observable<any>;
  }

  sacuvajRad(rad, taskId) {
    console.log(rad);
    return this.httpClient.post('http://localhost:8080/obrada/sacuvajRad/'.concat(taskId), rad) as Observable<any>;
  }

  sacuvajRadSaPdf(y, taskId) {
    return this.httpClient.post('http://localhost:8080/obrada/sacuvajRadSaPdf/'.concat(taskId), y) as Observable<any>;
  }

  getNOCasopis() {
    return this.httpClient.get('http://localhost:8080/obrada/getNOCasopis') as Observable<any>;
  }

  // ------------------------------------------------
  sledeciTaskKoautor(processId: string) {
    return this.httpClient.get('http://localhost:8080/obrada/sledeciTaskKoautor/'.concat(processId)) as Observable<any>;
  }

  sacuvajKoautore(rad, taskId) {
    console.log(rad);
    return this.httpClient.post('http://localhost:8080/obrada/sacuvajKoautore/'.concat(taskId), rad) as Observable<any>;
  }

  // -------------------------------------------------
  getTasksUser(processId: string, username: string) {
    return this.httpClient.get('http://localhost:8080/obrada/getTasksUser/'.concat(processId).concat('/').concat(username)) as Observable<any>;

  }

  completeTask(processId: string, task) {
    console.log(task.name);
    console.log(task.taskId);
    if (task.name === 'Obrada rada') // ukoliko se nalazi na urednikovom tasku obrada rada
    {
      window.location.href = 'pregledRadaUrednik/' + processId + '/' + task.taskId;
    }
    else if (task.name === 'Izbor dva recenzenta') // ukoliko je dosao do taska za izbor dva recenzenta
    {
      window.location.href = 'izborRecenzenata/' +  processId + '/' + task.taskId;
    }
    else { // ukoliko se nalazi na autorovom tasku
      window.location.href = 'izmenaRadaAutor/' + processId + '/' + task.taskId;

    }
  }

  // ---------------------------------------------------
  sledeciTaskPregledUrednik(processId: string) {
    return this.httpClient.get('http://localhost:8080/obrada/sledeciTaskPregledUrednik/'.concat(processId)) as Observable<any>;
  }

  sacuvajPregledUrednika(rad, taskId) {
    console.log(rad);
    return this.httpClient.post('http://localhost:8080/obrada/sacuvajPregledUrednika/'.concat(taskId), rad) as Observable<any>;
  }

  // -----------------------------------------------------
  sledeciTaskPregledPdfUrednik(processId: string) {
    return this.httpClient.get('http://localhost:8080/obrada/sledeciTaskPregledPdfUrednik/'.concat(processId)) as Observable<any>;
  }

  sacuvajPregledUrednikaPdf(rad, taskId) {
    console.log(rad);
    return this.httpClient.post('http://localhost:8080/obrada/sacuvajPregledUrednikaPdf/'.concat(taskId), rad) as Observable<any>;
  }

// -----------------------------------------------
  sledeciTaskAutorKorekcija(processId: string) {
    return this.httpClient.get('http://localhost:8080/obrada/sledeciTaskAutorKorekcija/'.concat(processId)) as Observable<any>;
  }

  sacuvajKorekcijuAutorSaPdf(y, taskId) {
    return this.httpClient.post('http://localhost:8080/obrada/sacuvajKorekcijuAutorSaPdf/'.concat(taskId), y) as Observable<any>;
  }

  // ----------------------------------------------
  getRecenzentiCasopis(processId: string) {
    return this.httpClient.get('http://localhost:8080/obrada/getRecenzentiCasopis/'.concat(processId)) as Observable<any>;
  }

  sledeciTaskIzborRec(processId: string) {
    return this.httpClient.get('http://localhost:8080/obrada/sledeciTaskIzborRec/'.concat(processId)) as Observable<any>;
  }

  sacuvajIzborRec(rad, taskId) {
    console.log(rad);
    return this.httpClient.post('http://localhost:8080/obrada/sacuvajIzborRec/'.concat(taskId), rad) as Observable<any>;
  }

  getRecenzentiNO(processId: string) {
    return this.httpClient.get('http://localhost:8080/obrada/getRecenzentiNO/'.concat(processId)) as Observable<any>;
  }

  sledeciTaskIzborFiltriranihRec(processId: string) {
    return this.httpClient.get('http://localhost:8080/obrada/sledeciTaskIzborFiltriranihRec/'.concat(processId)) as Observable<any>;
  }

  // pomocna
  trenutniKorisnik() {
    return this.httpClient.get('http://localhost:8080/obrada/trenutniKorisnik')as Observable<any>;
  }



}
