import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ALoginObradaComponent } from './a-login-obrada.component';

describe('ALoginObradaComponent', () => {
  let component: ALoginObradaComponent;
  let fixture: ComponentFixture<ALoginObradaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ALoginObradaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ALoginObradaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
