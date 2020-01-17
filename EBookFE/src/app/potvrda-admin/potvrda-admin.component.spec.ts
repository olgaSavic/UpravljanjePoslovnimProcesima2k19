import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PotvrdaAdminComponent } from './potvrda-admin.component';

describe('PotvrdaAdminComponent', () => {
  let component: PotvrdaAdminComponent;
  let fixture: ComponentFixture<PotvrdaAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PotvrdaAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PotvrdaAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
