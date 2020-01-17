import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AktivacijaCasopisAdminComponent } from './aktivacija-casopis-admin.component';

describe('AktivacijaCasopisAdminComponent', () => {
  let component: AktivacijaCasopisAdminComponent;
  let fixture: ComponentFixture<AktivacijaCasopisAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AktivacijaCasopisAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AktivacijaCasopisAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
