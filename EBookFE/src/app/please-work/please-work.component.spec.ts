import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PleaseWorkComponent } from './please-work.component';

describe('PleaseWorkComponent', () => {
  let component: PleaseWorkComponent;
  let fixture: ComponentFixture<PleaseWorkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PleaseWorkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PleaseWorkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
