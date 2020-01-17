import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinishPageCasopisComponent } from './finish-page-casopis.component';

describe('FinishPageCasopisComponent', () => {
  let component: FinishPageCasopisComponent;
  let fixture: ComponentFixture<FinishPageCasopisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinishPageCasopisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinishPageCasopisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
