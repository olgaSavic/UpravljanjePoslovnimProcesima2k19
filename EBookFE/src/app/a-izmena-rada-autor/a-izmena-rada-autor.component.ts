import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryService} from '../services/repository/repository.service';
import {ObradaService} from '../services/obrada/obrada.service';
import {FormSubmissionWithFileDto} from '../model/FormSubmissionWithFileDto';

@Component({
  selector: 'app-a-izmena-rada-autor',
  templateUrl: './a-izmena-rada-autor.component.html',
  styleUrls: ['./a-izmena-rada-autor.component.css']
})
export class AIzmenaRadaAutorComponent implements OnInit {



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

  private fileField = null;
  private fileName = null;

  constructor(private userService : UserService,
              private route: ActivatedRoute,
              protected  router: Router,
              private repositoryService : RepositoryService,
              private obradaService: ObradaService) {

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;

    const taskId = this.route.snapshot.params.taskId ;
    this.task = taskId;

    let x = obradaService.sledeciTaskAutorKorekcija(processInstanceId);

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
      if(property.toString() == "pdf") {
        value[property] = this.fileName;
      }
      for (const property in value) {

          o.push({fieldId : property, categories : value[property]});


        console.log('niz za slanje izgleda');
        console.log(o);
      }
      console.log(o);
    }

    console.log(o);

    let y = new FormSubmissionWithFileDto(o, this.fileField.toString(), this.fileName.toString());
    let x = this.obradaService.sacuvajKorekcijuAutorSaPdf(y, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res)

        alert("Uspesno ste izmenili rad!");
        // nakon ovoga treba da se loguje urednik, da pregleda rad
        this.router.navigateByUrl('loginDrugiObrada/' + this.processInstance);


      },
      err => {
        console.log("Doslo je do greske, pa rad nije uspesno izmenjen!");
      }
    );
  }

  fileChoserListener(files: FileList, field)
  {
    let fileToUpload = files.item(0);
    field.fileName = files.item(0).name;
    this.fileName = files.item(0).name;

    let fileReader = new FileReader();

    fileReader.onload = (e) => {

      field.value = fileReader.result;
      this.fileField = fileReader.result;
      console.log(fileReader.result);
    }

    fileReader.readAsDataURL(files.item(0))
  }



}
