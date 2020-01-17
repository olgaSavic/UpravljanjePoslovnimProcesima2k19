import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinishPageRecenzentComponent } from './finish-page-recenzent.component';

describe('FinishPageRecenzentComponent', () => {
  let component: FinishPageRecenzentComponent;
  let fixture: ComponentFixture<FinishPageRecenzentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinishPageRecenzentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinishPageRecenzentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
