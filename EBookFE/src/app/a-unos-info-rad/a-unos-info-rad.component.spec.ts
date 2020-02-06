import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AUnosInfoRadComponent } from './a-unos-info-rad.component';

describe('AUnosInfoRadComponent', () => {
  let component: AUnosInfoRadComponent;
  let fixture: ComponentFixture<AUnosInfoRadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AUnosInfoRadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AUnosInfoRadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
