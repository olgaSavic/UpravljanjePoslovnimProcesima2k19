import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AKrajOdbijenComponent } from './a-kraj-odbijen.component';

describe('AKrajOdbijenComponent', () => {
  let component: AKrajOdbijenComponent;
  let fixture: ComponentFixture<AKrajOdbijenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AKrajOdbijenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AKrajOdbijenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
