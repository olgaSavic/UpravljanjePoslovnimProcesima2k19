import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryService} from '../services/repository/repository.service';
import {ObradaService} from '../services/obrada/obrada.service';

@Component({
  selector: 'app-a-unos-info-rad',
  templateUrl: './a-unos-info-rad.component.html',
  styleUrls: ['./a-unos-info-rad.component.css']
})
export class AUnosInfoRadComponent implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  private naucneOblasti = [];

  constructor(private userService : UserService,
              private route: ActivatedRoute,
              protected  router: Router,
              private repositoryService : RepositoryService,
              private obradaService: ObradaService) {

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;

    let x = obradaService.unosInfoRad(processInstanceId);

    x.subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);

        this.obradaService.getNOCasopis().subscribe(
          pom => {
            console.log('Ispis no');
            console.log(pom);
            this.naucneOblasti = pom;
          }
        );
        this.formFields.forEach( (field) =>{

          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  ngOnInit() {
  }

  onSubmit(value, form){
    console.log(form);
    console.log(value);
    let o = new Array();

    for (const property in value) {

      if (property != 'naucnaOblastL') {
        o.push({fieldId : property, fieldValue : value[property]});
      } else {
        o.push({fieldId : property, categories : value[property]});

      }
      console.log(o);
    }

    console.log(o);
    let x = this.obradaService.sacuvajRad(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res)

        alert("Uspesno ste uneli informacije o radu!");
        this.router.navigateByUrl('unosKoautora/' + this.processInstance);

      },
      err => {
        console.log("Doslo je do greske, pa registracija nije izvrsena!");
      }
    );
  }


}
