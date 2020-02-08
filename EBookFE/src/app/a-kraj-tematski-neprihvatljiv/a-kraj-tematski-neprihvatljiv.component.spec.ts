import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AKrajTematskiNeprihvatljivComponent } from './a-kraj-tematski-neprihvatljiv.component';

describe('AKrajTematskiNeprihvatljivComponent', () => {
  let component: AKrajTematskiNeprihvatljivComponent;
  let fixture: ComponentFixture<AKrajTematskiNeprihvatljivComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AKrajTematskiNeprihvatljivComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AKrajTematskiNeprihvatljivComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
