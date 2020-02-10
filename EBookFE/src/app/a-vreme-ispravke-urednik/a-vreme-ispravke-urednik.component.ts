import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../services/auth/auth.service';
import {ObradaService} from '../services/obrada/obrada.service';

@Component({
  selector: 'app-a-vreme-ispravke-urednik',
  templateUrl: './a-vreme-ispravke-urednik.component.html',
  styleUrls: ['./a-vreme-ispravke-urednik.component.css']
})
export class AVremeIspravkeUrednikComponent implements OnInit {
  private formFieldsDto = null;
  private formFields = [];
  private processId = '';
  private username = '';
  private reviewers = [];
  private editors = [];
  private processInstance: any ;
  private enumValues = [];
  private enumValues2 = [];

  // tslint:disable-next-line:max-line-length
  constructor(private route: ActivatedRoute,
              protected router: Router,
              private authService: AuthService,
              private obradaService: ObradaService) {

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;

    const x = obradaService.sledeciTaskVrIspUrednik(processInstanceId);
    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);

        this.formFields.forEach( (field) =>{

          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
            this.reviewers = Object.keys(field.type.values);
          }

        });


      },
      err => {
        console.log('Error occured');
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


  onSubmit(value, form) {
    console.log(form);
    console.log(value);
    const o = new Array();

    // tslint:disable-next-line:forin
    for (const property in value) {

      console.log(o);

      if(value[property]=="PT10M" || value[property]=="PT5M" || value[property]=="PT3M") {
        o.push({fieldId: property, fieldValue: value[property]});
        console.log(o);
        let x = this.obradaService.sacuvajVrIspUrednika(o, this.formFieldsDto.taskId);

        x.subscribe(
          res => {
            alert("Zadato je vreme za manju ili vecu doradu rada");
            // treba da se loguje autor, da bi video komentare itd.
            this.router.navigateByUrl('loginDrugiObrada/' + this.processInstance);
          },
          err => {
            console.log("Error occured");
          }
        );
      }
    }

  }

}
