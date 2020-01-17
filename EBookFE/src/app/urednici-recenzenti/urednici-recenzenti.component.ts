import { Component, OnInit } from '@angular/core';

import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../services/users/user.service';
import {NaucnaOblastService} from '../services/naucna-oblast/naucna-oblast.service';
import {RepositoryService} from '../services/repository/repository.service';

@Component({
  selector: 'app-urednici-recenzenti',
  templateUrl: './urednici-recenzenti.component.html',
  styleUrls: ['./urednici-recenzenti.component.css']
})
export class UredniciRecenzentiComponent implements OnInit {

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
              private userService: UserService, private casopisService: NaucnaOblastService, private repositoryService: RepositoryService) {

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;

    const x = casopisService.sledeciTaskCasopis(processInstanceId);
    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);

        this.userService.getAllRecenzenti().subscribe(
          pom => {
            console.log('Ispis recenzenata');
            console.log(pom);
            this.reviewers = pom;
          }
        );

        this.userService.getAllUrednici().subscribe(
          red => {
            console.log('Ispis urednika');
            console.log(red);
            this.editors = red;
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

      if (property != 'uredniciL' && property != 'recenzentiL' ) {
        o.push({fieldId : property, fieldValue : value[property]});
      } else {
        o.push({fieldId : property, categories : value[property]});

      }
      console.log(o);
    }

    let x = this.casopisService.updateCasopis(o, this.formFieldsDto.taskId);
    console.log('Pre subscribe');
    x.subscribe(
      res => {
        console.log(res);
        alert('Izvrseno je uspesno dodavanje urednika naucnih oblasti i recenzenata!');
        this.router.navigateByUrl('loginAdminCasopis/' + this.processInstance);
      },
      err => {
        console.log('Error occured');
      }
    );
  }

}
