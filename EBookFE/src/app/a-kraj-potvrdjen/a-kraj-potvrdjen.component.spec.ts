import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AKrajPotvrdjenComponent } from './a-kraj-potvrdjen.component';

describe('AKrajPotvrdjenComponent', () => {
  let component: AKrajPotvrdjenComponent;
  let fixture: ComponentFixture<AKrajPotvrdjenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AKrajPotvrdjenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AKrajPotvrdjenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
