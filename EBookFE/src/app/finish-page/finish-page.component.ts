import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-finish-page',
  templateUrl: './finish-page.component.html',
  styleUrls: ['./finish-page.component.css']
})
export class FinishPageComponent implements OnInit {

  private processInstance: any ;

  constructor(private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;

  }

}
