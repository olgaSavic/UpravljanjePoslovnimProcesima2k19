import { Component, OnInit } from '@angular/core';
import {ObradaService} from '../services/obrada/obrada.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-a-kraj-tematski-neprihvatljiv',
  templateUrl: './a-kraj-tematski-neprihvatljiv.component.html',
  styleUrls: ['./a-kraj-tematski-neprihvatljiv.component.css']
})
export class AKrajTematskiNeprihvatljivComponent implements OnInit {

  constructor(private obradService: ObradaService,
              protected router: Router) { }

  ngOnInit() {


  }

}
