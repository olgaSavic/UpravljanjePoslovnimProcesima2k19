import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-finish-page-recenzent',
  templateUrl: './finish-page-recenzent.component.html',
  styleUrls: ['./finish-page-recenzent.component.css']
})
export class FinishPageRecenzentComponent implements OnInit {

  private processInstance: any ;
  constructor(private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;


  }

}
