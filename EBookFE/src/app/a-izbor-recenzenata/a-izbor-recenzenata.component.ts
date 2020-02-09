import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../services/users/user.service';
import {NaucnaOblastService} from '../services/naucna-oblast/naucna-oblast.service';
import {RepositoryService} from '../services/repository/repository.service';
import {ObradaService} from '../services/obrada/obrada.service';

@Component({
  selector: 'app-a-izbor-recenzenata',
  templateUrl: './a-izbor-recenzenata.component.html',
  styleUrls: ['./a-izbor-recenzenata.component.css']
})
export class AIzborRecenzenataComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processId = '';
  private username = '';
  private reviewers = [];
  private editors = [];
  private processInstance: any ;
  // tslint:disable-next-line:max-line-length
  constructor(private route: ActivatedRoute,
              protected router: Router,
              private obradaService: ObradaService) {

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;

    const x = obradaService.sledeciTaskIzborRec(processInstanceId);
    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);

        this.obradaService.getRecenzentiCasopis(this.processInstance).subscribe(
          pom => {
            console.log('Ispis recenzenata');
            console.log(pom);
            this.reviewers = pom;
          }
        );

      },
      err => {
        console.log('Error occured');
      }
    );

  }

  ngOnInit() {
  }


  onSubmit(value, form) {
    console.log(form);
    console.log(value);
    const o = new Array();

    // tslint:disable-next-line:forin
    for (const property in value) {

      if (property != 'recenzentiL' ) {
        o.push({fieldId : property, fieldValue : value[property]});
      } else {
        o.push({fieldId : property, categories : value[property]});

      }
      console.log(o);
    }

    let x = this.obradaService.sacuvajIzborRec(o, this.formFieldsDto.taskId);
    console.log('Pre subscribe');
    x.subscribe(
      res => {
        console.log(res);
        alert('Izvrsen je uspesan izbor recenzenata!');
        this.router.navigateByUrl('loginDrugiObrada/' + this.processInstance);
      },
      err => {
        console.log('Error occured');
      }
    );
  }

  filtriraj()
  {
    let x = this.obradaService.getRecenzentiNO(this.processInstance);

    x.subscribe(res => {
      this.reviewers = res;

    })

  }

}
