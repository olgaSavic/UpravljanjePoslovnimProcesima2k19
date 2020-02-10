import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AKonacnaOdlukaUrednikComponent } from './a-konacna-odluka-urednik.component';

describe('AKonacnaOdlukaUrednikComponent', () => {
  let component: AKonacnaOdlukaUrednikComponent;
  let fixture: ComponentFixture<AKonacnaOdlukaUrednikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AKonacnaOdlukaUrednikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AKonacnaOdlukaUrednikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
