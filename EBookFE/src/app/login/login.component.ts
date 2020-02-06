import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators, FormBuilder, NgForm, AbstractControl} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {KorisnikService} from '../services/korisnik/korisnik.service';
import {KorisnikModel} from '../model/Korisnik.model';
import {AuthService} from '../services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm = this.formBuilder.group({username: ['', Validators.required],
    password: ['', [Validators.required,
      Validators.minLength(2),
      Validators.maxLength(50)]] });

  loginError = '';
  sendUser: KorisnikModel = new KorisnikModel();

  constructor(private route: ActivatedRoute,
              protected router: Router,
              private formBuilder: FormBuilder, private userService: KorisnikService,
              private authService: AuthService) { }

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
              alert('Uspesno ste se ulogovali!');
              localStorage.setItem("ROLE", data.tip);
              localStorage.setItem("USERNAME", data.username);

              // dodati pitanje u zavisnosti od kog ce se prelaziti na kreiranje casopisa ili na dodavanje novog teksta
              if (data.tip == "UREDNIK") {
                this.router.navigateByUrl('kreiranjeCasopisa');
              }
              else
              {
                this.router.navigateByUrl('');
              }


            }
          )
        }
      }
    );

  }

  registrujSe()
  {
    this.router.navigateByUrl('/registrate');
  }

}
