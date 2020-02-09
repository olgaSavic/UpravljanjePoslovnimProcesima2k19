import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryService} from '../services/repository/repository.service';
import {ObradaService} from '../services/obrada/obrada.service';

@Component({
  selector: 'app-a-unos-koautora',
  templateUrl: './a-unos-koautora.component.html',
  styleUrls: ['./a-unos-koautora.component.css']
})
export class AUnosKoautoraComponent implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  private dalje: any ;

  private naucneOblasti = [];

  constructor(private userService : UserService,
              private route: ActivatedRoute,
              protected  router: Router,
              private repositoryService : RepositoryService,
              private obradaService: ObradaService) {

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;

    let x = obradaService.sledeciTaskKoautor(processInstanceId);

    x.subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);

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

    let o = new Array();

    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});

      if (property == 'dodatniKoautor')
      {
        this.dalje = value[property];
      }
    }


    console.log(o);
    let x = this.obradaService.sacuvajKoautore(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res)

        alert("Uspesno ste uneli koautora u rad!");

        if (this.dalje == false) { // zavrsen je unos koautora
          this.router.navigateByUrl('loginDrugiObrada/' + this.processInstance);
        }
        else {
          location.reload();
        }


      },
      err => {
        console.log("Doslo je do greske, pa koautor nije dodat u rad!");
      }
    );
  }


}
