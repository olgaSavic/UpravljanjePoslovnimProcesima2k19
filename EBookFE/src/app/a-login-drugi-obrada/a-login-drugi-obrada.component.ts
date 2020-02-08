import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {KorisnikModel} from '../model/Korisnik.model';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../services/auth/auth.service';
import {KorisnikService} from '../services/korisnik/korisnik.service';

@Component({
  selector: 'app-a-login-drugi-obrada',
  templateUrl: './a-login-drugi-obrada.component.html',
  styleUrls: ['./a-login-drugi-obrada.component.css']
})
export class ALoginDrugiObradaComponent implements OnInit {


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

              this.router.navigateByUrl('homepage/' + this.processInstance);

            }
          )
        }
      }
    );

  }



}
