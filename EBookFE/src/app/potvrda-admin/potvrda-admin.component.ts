import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryService} from '../services/repository/repository.service';
import {NaucnaOblastService} from '../services/naucna-oblast/naucna-oblast.service';
import {KorisnikService} from '../services/korisnik/korisnik.service';

@Component({
  selector: 'app-potvrda-admin',
  templateUrl: './potvrda-admin.component.html',
  styleUrls: ['./potvrda-admin.component.css']
})
export class PotvrdaAdminComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];

  private hide : any;

  private dodatnaNaucna: any;
  private dalje: any ;


  constructor(private userService : UserService,
              private route: ActivatedRoute,
              protected  router: Router,
              private repositoryService : RepositoryService,
              private naucnaOService: NaucnaOblastService,
              private korisnikService: KorisnikService) {


    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;


    alert('POZVAN RECENZENT!');
    let y = this.korisnikService.noviRecenzent(processInstanceId);

    alert('POZVAN RECENZENT!');

    y.subscribe((
      res => {

        alert('U metodi sam!');
        this.formFieldsDto = res;
        this.formFields = res.formFields;

        this.formFields.forEach( (field) =>{

          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });
      }
    ))

  }

  ngOnInit() {

    alert('POZVAN RECENZENT!');
    alert ('TU SAM!');

  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});

      if (property == 'potvrdaRecenzenta')
      {
        this.dalje = value[property];
      }

    }

    console.log(o);
    let x = this.korisnikService.posaljiRecenzenta(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);

        if (this.dalje == true)
        {
          this.router.navigateByUrl('finishPageRecenzent');
        }
        else
        {
          this.router.navigateByUrl('finishPage');
        }



      },
      err => {
        console.log("Doslo je do greske, pa potvrda recenzenta nije izvrsena!");
      }
    );
  }



}
