import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ObradaService} from '../services/obrada/obrada.service';
import {RepositoryService} from '../services/repository/repository.service';
import {NaucnaOblastService} from '../services/naucna-oblast/naucna-oblast.service';

@Component({
  selector: 'app-a-konacna-odluka-urednik',
  templateUrl: './a-konacna-odluka-urednik.component.html',
  styleUrls: ['./a-konacna-odluka-urednik.component.css']
})
export class AKonacnaOdlukaUrednikComponent implements OnInit {


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
    let y = this.obradaService.nextTaskOdlukaGlUrednik(processInstanceId);
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
      if (property != 'konacnaOdlukaZaObjavljivanjeL') {
        o.push({fieldId : property, fieldValue : value[property]});
      } else {
        o.push({fieldId : property, categories : value[property]});


      }



    }

    console.log(o);
    let x = this.obradaService.sacuvajOdlukaGlUrednik(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.router.navigateByUrl('loginDrugiObrada/' + this.processInstance);


      },
      err => {
        console.log("Doslo je do greske");
      }
    );

    /*
    let nova = this.obradaService.doradaDalje(this.processInstance);
    nova.subscribe(olga => {

      alert("USAO")!
      alert(olga.povratna);
      alert(olga);
      if (olga == "kraj")
      {
        this.router.navigateByUrl('krajPotvrdjen');

      }
      else if (olga == "odbiti")
      {
        this.router.navigateByUrl('krajOdbijen');
      }
      else { // manja ili veca dorada
        this.router.navigateByUrl('loginDrugiObrada/' + this.processInstance);
      }
    })

    */

  }


}
