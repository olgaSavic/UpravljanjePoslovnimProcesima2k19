import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryService} from '../services/repository/repository.service';
import {NaucnaOblastService} from '../services/naucna-oblast/naucna-oblast.service';
import {ObradaService} from '../services/obrada/obrada.service';

@Component({
  selector: 'app-a-potvrda-nastavak',
  templateUrl: './a-potvrda-nastavak.component.html',
  styleUrls: ['./a-potvrda-nastavak.component.css']
})
export class APotvrdaNastavakComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];

  private hide : any;

  private dodatnaNaucna: any;

  constructor(private userService : UserService,
              private route: ActivatedRoute,
              protected  router: Router,
              private obradaService: ObradaService,
              private repositoryService : RepositoryService,
              private naucnaOService: NaucnaOblastService) {


    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    let y = this.obradaService.potvrdaNastavak(processInstanceId);
    this.processInstance = processInstanceId;

    y.subscribe((
      res => {
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

    this.hide = false ;
  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});

    }

    console.log(o);
    let x = this.obradaService.sacuvajIzborNastavak(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);

        this.router.navigateByUrl('izborCasopisa/' + this.processInstance);

      },
      err => {
        console.log("Doslo je do greske");
      }
    );
  }


}
