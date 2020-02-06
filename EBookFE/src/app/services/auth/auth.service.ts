import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { tap, mapTo, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import * as jwt_decode from 'jwt-decode';
import { Router } from '@angular/router';
import {KorisnikModel} from '../../model/Korisnik.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService{



  constructor(private http : HttpClient, private router : Router){
  }

  login(user : KorisnikModel) : Observable<boolean>{



    return this.http.post<any>("api/login", {username: user.username, password: user.password})
      .pipe(
        tap(response => this.doLoginUser(response)),
        mapTo(true),
        catchError(error => {
          return of(false);
        }));
  }
  getCurrentUser() {
    return this.http.get<any>('api/korisnik/trenutniKorisnik');
  }
  doLoginUser(response){
    localStorage.setItem("AGENT_JWT_TOKEN", response.jwt);
    /*
    this.http.get('/api/syncAll').subscribe(data => {
      console.log(data);
    },
      error => {
      console.log(error);
      }
      )
     */
  }

  logOutUser(){
    localStorage.removeItem("AGENT_JWT_TOKEN");
    localStorage.removeItem("ROLE");
    localStorage.removeItem("USERNAME");
    this.router.navigateByUrl('')
  }

  isUserLogged() : boolean{
    let jwt = localStorage.getItem("AGENT_JWT_TOKEN");
    if(jwt == null) return false;
    else if(!this.isTokenExpired()) return true;
  }

  getJwt() : string{
    return localStorage.getItem("AGENT_JWT_TOKEN");
  }

  getTokenExpirationDate(token: string): Date {
    const decoded = jwt_decode(token);

    if (decoded.exp === undefined) return null;

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(token?: string): boolean {
    if(!token) token = this.getJwt();
    if(!token) return true;

    const date = this.getTokenExpirationDate(token);
    if(date === undefined) return false;
    return !(date.valueOf() > new Date().valueOf());
  }
}
