import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators, FormBuilder, NgForm, AbstractControl} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {KorisnikService} from '../services/korisnik/korisnik.service';
import {Korisnik} from '../model/Korisnik';

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
  sendUser: Korisnik = new Korisnik();

  constructor(private route: ActivatedRoute,
              protected router: Router,
              private formBuilder: FormBuilder, private userService: KorisnikService) { }

  ngOnInit() {
  }


  login(submittedForm: FormGroup) {
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
        this.router.navigateByUrl('kreiranjeCasopisa');
      },
      err => {
        alert('Uneli ste neispravno korisnicko ime ili lozinku, ili nemate dozvolu da se logujete preko ove forme!');
        location.reload();
      }
    );
  }

  registrujSe()
  {
    this.router.navigateByUrl('/registrate');
  }

}
