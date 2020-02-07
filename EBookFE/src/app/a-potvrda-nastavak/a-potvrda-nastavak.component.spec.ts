import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { APotvrdaNastavakComponent } from './a-potvrda-nastavak.component';

describe('APotvrdaNastavakComponent', () => {
  let component: APotvrdaNastavakComponent;
  let fixture: ComponentFixture<APotvrdaNastavakComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ APotvrdaNastavakComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(APotvrdaNastavakComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
