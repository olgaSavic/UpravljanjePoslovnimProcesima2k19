import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ARecenziranjeUrednikComponent } from './a-recenziranje-urednik.component';

describe('ARecenziranjeUrednikComponent', () => {
  let component: ARecenziranjeUrednikComponent;
  let fixture: ComponentFixture<ARecenziranjeUrednikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ARecenziranjeUrednikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ARecenziranjeUrednikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
