import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ALoginDrugiObradaComponent } from './a-login-drugi-obrada.component';

describe('ALoginDrugiObradaComponent', () => {
  let component: ALoginDrugiObradaComponent;
  let fixture: ComponentFixture<ALoginDrugiObradaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ALoginDrugiObradaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ALoginDrugiObradaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
