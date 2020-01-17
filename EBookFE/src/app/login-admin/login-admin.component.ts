import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Korisnik} from '../model/Korisnik';
import {ActivatedRoute, Router} from '@angular/router';
import {KorisnikService} from '../services/korisnik/korisnik.service';

@Component({
  selector: 'app-login-admin',
  templateUrl: './login-admin.component.html',
  styleUrls: ['./login-admin.component.css']
})
export class LoginAdminComponent implements OnInit {

  loginForm = this.formBuilder.group({username: ['', Validators.required],
    password: ['', [Validators.required,
      Validators.minLength(2),
      Validators.maxLength(50)]] });

  loginError = '';
  sendUser: Korisnik = new Korisnik();
  idThis: any ;
  private processInstance = "";

  constructor(private route: ActivatedRoute,
              protected router: Router,
              private formBuilder: FormBuilder, private userService: KorisnikService) {

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;
  }

  ngOnInit() {
  }


  login(submittedForm: FormGroup) {
    const username = submittedForm.get('username').value;
    const password = submittedForm.get('password').value;

    this.sendUser.username = username;
    this.sendUser.password = password;

    let x = this.userService.loginAdmin(this.sendUser);
    x.subscribe(
      res => {
        console.log(res);
        alert('Uspesno ste se ulogovali!');
        sessionStorage.setItem('loggedUser', JSON.stringify(res));
        this.router.navigateByUrl('recAdmin/' + this.processInstance);
      },
      err => {
        alert('Uneli ste neispravno korisnicko ime ili lozinku, ili nemate dozvolu da se logujete preko ove forme!');
        location.reload();
      }
    );
  }


}
