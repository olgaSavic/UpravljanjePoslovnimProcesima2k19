import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NaucnaOblastCasopisComponent } from './naucna-oblast-casopis.component';

describe('NaucnaOblastCasopisComponent', () => {
  let component: NaucnaOblastCasopisComponent;
  let fixture: ComponentFixture<NaucnaOblastCasopisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NaucnaOblastCasopisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NaucnaOblastCasopisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
