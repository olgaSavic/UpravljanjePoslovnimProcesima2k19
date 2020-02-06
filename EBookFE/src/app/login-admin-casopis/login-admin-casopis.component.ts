import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Korisnik} from '../model/Korisnik';
import {ActivatedRoute, Router} from '@angular/router';
import {KorisnikService} from '../services/korisnik/korisnik.service';
import {KorisnikModel} from '../model/Korisnik.model';
import {AuthService} from '../services/auth/auth.service';

@Component({
  selector: 'app-login-admin-casopis',
  templateUrl: './login-admin-casopis.component.html',
  styleUrls: ['./login-admin-casopis.component.css']
})
export class LoginAdminCasopisComponent implements OnInit {

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
          location.reload();

        } else {
          this.authService.getCurrentUser().subscribe(
            data => {
              alert('Uspesno ste se ulogovali kao admin!');
              localStorage.setItem("ROLE", data.tip);
              localStorage.setItem("USERNAME", data.username);
              this.router.navigateByUrl('aktivacijaCasAdmin/' + this.processInstance);

            }
          )
        }
      }
    );

  }



  login2(submittedForm: FormGroup) {
    const username = submittedForm.get('username').value;
    const password = submittedForm.get('password').value;

    this.sendUser.username = username;
    this.sendUser.password = password;

    let x = this.userService.loginAdmin(this.sendUser);
    x.subscribe(
      res => {
        console.log(res);
        alert('Uspesno ste se ulogovali kao admin!');
        sessionStorage.setItem('loggedUser', JSON.stringify(res));
        this.router.navigateByUrl('aktivacijaCasAdmin/' + this.processInstance);
      },
      err => {
        alert('Uneli ste neispravno korisnicko ime ili lozinku, ili nemate dozvolu da se logujete preko ove forme!');
        location.reload();
      }
    );
  }


}
