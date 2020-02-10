import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AAUrednikIspComponent } from './a-a-urednik-isp.component';

describe('AAUrednikIspComponent', () => {
  let component: AAUrednikIspComponent;
  let fixture: ComponentFixture<AAUrednikIspComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AAUrednikIspComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AAUrednikIspComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
