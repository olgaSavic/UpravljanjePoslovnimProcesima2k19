import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class KorisnikService {

  constructor(private httpClient: HttpClient, private http : Http) {
  }

  private BASE_URL = 'http://localhost:8080/email';
  private OLGA = 'http://localhost:8080/recenzentController';
  private NEMANJA = 'http://localhost:8080/aktivacijaCasopis';


  loginUser(user) {
    console.log(user);
    return this.httpClient.post('http://localhost:8080/korisnik/login', user) as Observable<any>;
  }

  loginAdmin(user) {
    console.log(user);
    return this.httpClient.post('http://localhost:8080/korisnik/loginAdmin', user) as Observable<any>;
  }

  predjiDalje(email: any) : Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});

    return this.httpClient.get(`http://localhost:8080/korisnik/predjiDalje/${email}`, {headers});
  }

  logoutUser() {
    console.log('U logout je');
    return this.httpClient.get('http://localhost:8080/korisnik/logout') as Observable<any>;
  }

  // EMAIL SLANJE
  aktivirajNalog(email: any){
    return this.httpClient.get(`${this.BASE_URL}/aktivirajNalog/${email}`) as Observable<any> ;
  }

  // admin potvrda recenzenta
  noviRecenzent(id: any){
    return this.httpClient.get(`${this.OLGA}/noviRecenzent/${id}`) as Observable<any> ;
  }

  novaNO(id: any){
    return this.httpClient.get(`${this.BASE_URL}/novaNO/${id}`) as Observable<any> ;
  }

  posaljiRecenzenta(no, taskId) {
    return this.httpClient.post("http://localhost:8080/recenzentController/posaljiRecenzenta/".concat(taskId), no) as Observable<any>;
  }

  // admin aktivacija casopisa
  noviAktivan(id: any){
    return this.httpClient.get(`${this.NEMANJA}/noviAktivan/${id}`) as Observable<any> ;
  }

  posaljiAktivan(no, taskId) {
    return this.httpClient.post("http://localhost:8080/aktivacijaCasopis/posaljiAktivan/".concat(taskId), no) as Observable<any>;
  }




}
