import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NaucnaOblastComponent } from './naucna-oblast.component';

describe('NaucnaOblastComponent', () => {
  let component: NaucnaOblastComponent;
  let fixture: ComponentFixture<NaucnaOblastComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NaucnaOblastComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NaucnaOblastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
