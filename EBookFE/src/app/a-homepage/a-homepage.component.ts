import { Component, OnInit } from '@angular/core';
import {KorisnikModel} from '../model/Korisnik.model';
import {AuthService} from '../services/auth/auth.service';
import {ObradaService} from '../services/obrada/obrada.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-a-homepage',
  templateUrl: './a-homepage.component.html',
  styleUrls: ['./a-homepage.component.css']
})
export class AHomepageComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = '';
  private tasks = [];
  private user: KorisnikModel;
  private type = '';
  private username : any;
  private korisnickoIme = '';


  constructor(private authService: AuthService,
              private obradaService: ObradaService,
              protected route: ActivatedRoute)
  {

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;

    this.authService.getCurrentUser().subscribe(
      data => {

        this.korisnickoIme = data.username ;
        let x = this.obradaService.getTasksUser(this.processInstance, this.korisnickoIme);

        x.subscribe(
          res => {
            console.log(res);
            this.tasks = res;
          },
          err => {
            console.log('Error occured');
          }
        );


      }
    )



  }
  ngOnInit() {

  }

  complete(taskId){
    let x = this.obradaService.completeTask(this.processInstance, taskId);
    console.log('Usao u complete task');

  }



}
