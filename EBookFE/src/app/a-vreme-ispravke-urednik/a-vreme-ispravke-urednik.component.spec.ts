import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AVremeIspravkeUrednikComponent } from './a-vreme-ispravke-urednik.component';

describe('AVremeIspravkeUrednikComponent', () => {
  let component: AVremeIspravkeUrednikComponent;
  let fixture: ComponentFixture<AVremeIspravkeUrednikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AVremeIspravkeUrednikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AVremeIspravkeUrednikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
