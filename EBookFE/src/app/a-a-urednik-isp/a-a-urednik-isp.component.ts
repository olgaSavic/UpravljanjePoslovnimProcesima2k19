import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../services/auth/auth.service';
import {RepositoryService} from '../services/repository/repository.service';
import {ObradaService} from '../services/obrada/obrada.service';

@Component({
  selector: 'app-a-a-urednik-isp',
  templateUrl: './a-a-urednik-isp.component.html',
  styleUrls: ['./a-a-urednik-isp.component.css']
})
export class AAUrednikIspComponent implements OnInit {


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

    let x = obradaService.sledeciTaskIspZahteviUrednik(processInstanceId);

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

      if (property == 'prihRadObj')
      {
        this.dalje = value[property];
      }
    }


    console.log(o);
    let x = this.obradaService.sacuvajIspZahteveUrednika(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res)

        alert("Uspesno ste pregledali rad!");

          if (this.dalje == true)
          {
            this.router.navigateByUrl('krajPotvrdjen');
          }
          else
          {
            this.router.navigateByUrl('loginDrugiObrada/' + this.processInstance);
          }



      },
      err => {
        console.log("Doslo je do greske, pa rad nije uspesno pregledan!");
      }
    );
  }


}
