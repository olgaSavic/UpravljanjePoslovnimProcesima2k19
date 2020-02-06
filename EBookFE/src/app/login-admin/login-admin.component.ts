import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Korisnik} from '../model/Korisnik';
import {ActivatedRoute, Router} from '@angular/router';
import {KorisnikService} from '../services/korisnik/korisnik.service';
import {KorisnikModel} from '../model/Korisnik.model';
import {AuthService} from '../services/auth/auth.service';

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
  sendUser: KorisnikModel = new KorisnikModel();
  idThis: any ;
  private processInstance = "";

  constructor(private route: ActivatedRoute,
              protected router: Router,
              private authService: AuthService,
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

    let x = this.authService.login(this.sendUser);
    x.subscribe(
      success => {

        if (!success) {
          alert('Neispravno korisnicko ime ili lozinka!');

        } else {
          this.authService.getCurrentUser().subscribe(
            data => {

              localStorage.setItem("ROLE", data.tip);
              localStorage.setItem("USERNAME", data.username);
              this.router.navigateByUrl('recAdmin/' + this.processInstance);

            }
          )
        }
      }
    );

  }



}
