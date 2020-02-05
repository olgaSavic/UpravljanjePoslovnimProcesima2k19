import { Component, OnInit } from '@angular/core';
import {ObradaService} from '../services/obrada/obrada.service';

@Component({
  selector: 'app-a-zapocni-obradu',
  templateUrl: './a-zapocni-obradu.component.html',
  styleUrls: ['./a-zapocni-obradu.component.css']
})
export class AZapocniObraduComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  constructor(private obradaService: ObradaService) {

    let x = obradaService.startObradaProcess();
    x.subscribe(
      res => {
        console.log(res);
        console.log('Ispis rezultata');
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);

        this.processInstance = res.processInstanceId;
        this.formFields.forEach((field) => {
          if (field.type.name === 'enum') {
            this.enumValues = Object.keys(field.type.values);
          }
        });
      });
  }

  ngOnInit() {


  }

}
