import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AAAutorNovaIspComponent } from './a-a-autor-nova-isp.component';

describe('AAAutorNovaIspComponent', () => {
  let component: AAAutorNovaIspComponent;
  let fixture: ComponentFixture<AAAutorNovaIspComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AAAutorNovaIspComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AAAutorNovaIspComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
