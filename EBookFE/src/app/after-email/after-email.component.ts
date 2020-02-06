import { Component, OnInit } from '@angular/core';
import {KorisnikService} from '../services/korisnik/korisnik.service';
import {UserService} from '../services/users/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryService} from '../services/repository/repository.service';
import {NaucnaOblastService} from '../services/naucna-oblast/naucna-oblast.service';
import {error} from 'selenium-webdriver';

@Component({
  selector: 'app-after-email',
  templateUrl: './after-email.component.html',
  styleUrls: ['./after-email.component.css']
})
export class AfterEmailComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  private tekst: any ;
  private emailDalje: any ;

  private dodatnaNaucna: any;
  private hide: any ;

  constructor(private userService : UserService,
              private route: ActivatedRoute,
              protected  router: Router,
              private repositoryService : RepositoryService,
              private naucnaOService: NaucnaOblastService,
              private korisnikService: KorisnikService) {


    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    const email = this.route.snapshot.params.email ;
    this.emailDalje = email;

    this.processInstance = processInstanceId;
    let y = this.korisnikService.aktivirajNalog(email); // metoda vraca string

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

  nastaviDalje()
  {
    let nova = this.emailDalje;

    this.korisnikService.predjiDalje(nova).subscribe(data => {

        this.router.navigateByUrl('/loginAdmin/' + this.processInstance);

    },
      err => {
        this.router.navigateByUrl('/finishPage/' + this.processInstance);
      }
      )



    /*

    y.subscribe(res => {

      alert('Res je: ' + res);
      if (res == true)
      {
        this.router.navigateByUrl('/loginAdmin');
      }
      else {
        this.router.navigateByUrl('/finishPage');
      }
    })
    */




  }




}
