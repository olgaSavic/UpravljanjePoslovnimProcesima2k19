import { Component, OnInit } from '@angular/core';
import {ObradaService} from '../services/obrada/obrada.service';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryService} from '../services/repository/repository.service';
import {NaucnaOblastService} from '../services/naucna-oblast/naucna-oblast.service';
import {KorisnikService} from '../services/korisnik/korisnik.service';
import {AuthService} from '../services/auth/auth.service';

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

  private dalje: any ;

  constructor(private userService : UserService,
              private route: ActivatedRoute,
              protected  router: Router,
              private authService: AuthService,
              private repositoryService : RepositoryService,
              private naucnaOService: NaucnaOblastService,
              private korisnikService: KorisnikService,
              private obradaService: ObradaService) {

    let x = obradaService.startObradaProcess();

    // kupljenje polja forme
    x.subscribe(
      res => {
        this.processInstance = res.processInstanceId;
        this.authService.getCurrentUser().subscribe(
          data => {

            // neko je ulogovan
            if (data != null) {
              this.router.navigateByUrl('izborCasopisa/' + this.processInstance);
            }
            localStorage.setItem("PROCES_INSTANCE_ID", this.processInstance);

          }
        )
        console.log(res);
        console.log('Ispis rezultata');
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);
        this.formFields.forEach((field) => {
          if (field.type.name === 'enum') {
            this.enumValues = Object.keys(field.type.values);
          }
        });


      });



  }

  ngOnInit() {

  }

  onSubmit(value, form)
  {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});

      if (property == 'zelimReg')
      {
        this.dalje = value[property];
      }
    }

    console.log(o);
    let x = this.obradaService.nastaviDaljeReg(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);



        if (this.dalje == true)
        {
          this.router.navigateByUrl('registrate');
        }
        else
        {
          this.router.navigateByUrl('loginObrada/' + this.processInstance);
        }

      },
      err => {
        console.log("Doslo je do greske, pa nije moguc nastavak!");
      }
    );
  }


}
