import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryService} from '../services/repository/repository.service';
import {NaucnaOblastService} from '../services/naucna-oblast/naucna-oblast.service';

@Component({
  selector: 'app-naucna-oblast-casopis',
  templateUrl: './naucna-oblast-casopis.component.html',
  styleUrls: ['./naucna-oblast-casopis.component.css']
})
export class NaucnaOblastCasopisComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];

  private dodatnaNaucna: any;

  constructor(private userService : UserService,
              private route: ActivatedRoute,
              protected  router: Router,
              private repositoryService : RepositoryService,
              private naucnaOService: NaucnaOblastService) {


    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;
    let y = this.naucnaOService.novaNOCasopis(processInstanceId);

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
  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});

      if (property == 'dodatnaNO')
      {
        this.dodatnaNaucna = value[property];
      }

    }

    console.log(o);
    let x = this.naucnaOService.posaljiNOCasopis(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);

          alert("Uspesno je sacuvana nova naucna oblast!");

          if (this.dodatnaNaucna == true)
          {
            location.reload();
          }
          else
          {
            this.router.navigateByUrl('uredRec/' + this.processInstance);
          }


      },
      err => {
        console.log("Doslo je do greske, pa dodavanje naucne oblasti nije izvrseno!");
      }
    );
  }

}
