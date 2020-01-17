import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginAdminCasopisComponent } from './login-admin-casopis.component';

describe('LoginAdminCasopisComponent', () => {
  let component: LoginAdminCasopisComponent;
  let fixture: ComponentFixture<LoginAdminCasopisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginAdminCasopisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginAdminCasopisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
