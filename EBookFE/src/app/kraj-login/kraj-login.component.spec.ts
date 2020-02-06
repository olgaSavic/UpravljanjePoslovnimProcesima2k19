import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KrajLoginComponent } from './kraj-login.component';

describe('KrajLoginComponent', () => {
  let component: KrajLoginComponent;
  let fixture: ComponentFixture<KrajLoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KrajLoginComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KrajLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
