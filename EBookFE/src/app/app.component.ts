import { Component } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ObradaService} from './services/obrada/obrada.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private route: ActivatedRoute,
              protected router: Router,
              private obradaService: ObradaService) {

  }
  title = 'app';
  private user = JSON.parse(localStorage.getItem('user'));
  private role = localStorage.getItem('role');

  loggedIn(){
    if(this.user){
      return true; 
    }else{
      return false;
    }
  }

  notLoggedIn(){
    if(!this.user){
      return true; 
    }else{
      return false;
    }
  }

  isAdmin(){
    if(this.role == "ADMIN"){
      return true; 
    }else{
      return false;
    }
  }

  logout()
  {
    localStorage.removeItem("AGENT_JWT_TOKEN");
    localStorage.removeItem("ROLE");
    localStorage.removeItem("USERNAME");
    this.router.navigateByUrl('')
  }
}
