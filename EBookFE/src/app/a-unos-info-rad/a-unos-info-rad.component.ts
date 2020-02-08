import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryService} from '../services/repository/repository.service';
import {ObradaService} from '../services/obrada/obrada.service';
import {FormSubmissionWithFileDto} from '../model/FormSubmissionWithFileDto';

@Component({
  selector: 'app-a-unos-info-rad',
  templateUrl: './a-unos-info-rad.component.html',
  styleUrls: ['./a-unos-info-rad.component.css']
})
export class AUnosInfoRadComponent implements OnInit {

  private formSubmissionWithFile  = null;
  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  private fileField = null;
  private fileName = null;

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
      if(property.toString() == "pdf") {
        value[property] = this.fileName;
      }
      o.push({fieldId : property, fieldValue : value[property]});

      console.log(o);
    }

    console.log(o);
    let y = new FormSubmissionWithFileDto(o, this.fileField.toString(), this.fileName.toString());

    //let x = this.obradaService.form(o, this.formFieldsDto.taskId);
    let x = this.obradaService.sacuvajRadSaPdf(y, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res)

        alert("Uspesno ste uneli informacije o radu!");
        this.router.navigateByUrl('unosKoautora/' + this.processInstance);

      },
      err => {
        console.log("Doslo je do greske, pa dodavanje novog rada nije izvrseno!");
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
