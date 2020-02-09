import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryService} from '../services/repository/repository.service';
import {ObradaService} from '../services/obrada/obrada.service';
import {AuthService} from '../services/auth/auth.service';

@Component({
  selector: 'app-a-pregled-rada-gl-urednik',
  templateUrl: './a-pregled-rada-gl-urednik.component.html',
  styleUrls: ['./a-pregled-rada-gl-urednik.component.css']
})
export class APregledRadaGlUrednikComponent implements OnInit {


  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";

  private enumValues = [];
  private tasks = [];

  private naucneOblasti = [];
  private task = "";

  private dalje: any ;

  constructor(private userService : UserService,
              private route: ActivatedRoute,
              protected  router: Router,
              private authService: AuthService,
              private repositoryService : RepositoryService,
              private obradaService: ObradaService) {

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;

    const taskId = this.route.snapshot.params.taskId ;
    this.task = taskId;

    let x = obradaService.sledeciTaskPregledUrednik(processInstanceId);

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

    this.authService.getCurrentUser().subscribe(
      data => {

        if(data.tip != "UREDNIK"){
          this.router.navigate(["login"]);
        }

      },
      error => {
        this.router.navigate(["login"]);
      }
    )

  }

  onSubmit(value, form){

    let o = new Array();

    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});

      if (property == 'tematskiPrihvatljiv')
      {
        this.dalje = value[property];
      }
    }


    console.log(o);
    let x = this.obradaService.sacuvajPregledUrednika(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res)

        alert("Uspesno ste pregledali rad!");

        if (this.dalje == true) { // rad je tematski prihvatljiv, pa se nastavlja na dalju proveru pdf-a
          this.router.navigateByUrl('pregledPdfUrednik/' + this.processInstance);
        }
        else // proces se terminira
        {
          this.router.navigateByUrl('krajTematskiNeprihvatljiv');
        }

      },
      err => {
        console.log("Doslo je do greske, pa rad nije uspesno pregledan!");
      }
    );
  }


}
