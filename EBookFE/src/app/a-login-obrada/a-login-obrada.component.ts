import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators, FormBuilder, NgForm, AbstractControl} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {KorisnikService} from '../services/korisnik/korisnik.service';
import {Korisnik} from '../model/Korisnik';
import {KorisnikModel} from '../model/Korisnik.model';
import {AuthService} from '../services/auth/auth.service';
@Component({
  selector: 'app-a-login-obrada',
  templateUrl: './a-login-obrada.component.html',
  styleUrls: ['./a-login-obrada.component.css']
})
export class ALoginObradaComponent implements OnInit {

  loginForm = this.formBuilder.group({username: ['', Validators.required],
    password: ['', [Validators.required,
      Validators.minLength(2),
      Validators.maxLength(50)]] });

  loginError = '';
  sendUser: KorisnikModel = new KorisnikModel();

  private processInstance: any ;

  constructor(private route: ActivatedRoute,
              protected router: Router,
              private authService: AuthService,
              private formBuilder: FormBuilder, private userService: KorisnikService) { }

  ngOnInit() {
  }

  login(submittedForm: FormGroup) {

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;
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
              alert('Uspesno ste se ulogovali!');
              localStorage.setItem("ROLE", data.tip);
              localStorage.setItem("USERNAME", data.username);

              this.router.navigateByUrl('potvrdaNastavak/' + this.processInstance);

            }
          )
        }
      }
    );

  }



  login2(submittedForm: FormGroup) {

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;

    const username = submittedForm.get('username').value;
    const password = submittedForm.get('password').value;

    this.sendUser.username = username;
    this.sendUser.password = password;

    let x = this.userService.loginUser(this.sendUser);
    x.subscribe(
      res => {
        console.log(res);
        alert('Uspesno ste se ulogovali!');
        sessionStorage.setItem('loggedUser', JSON.stringify(res));
        this.router.navigateByUrl('izborCasopisa/' + this.processInstance);
      },
      err => {
        alert('Uneli ste neispravno korisnicko ime ili lozinku!');
        location.reload();
      }
    );
  }


}
