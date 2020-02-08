import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { APregledRadaGlUrednikComponent } from './a-pregled-rada-gl-urednik.component';

describe('APregledRadaGlUrednikComponent', () => {
  let component: APregledRadaGlUrednikComponent;
  let fixture: ComponentFixture<APregledRadaGlUrednikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ APregledRadaGlUrednikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(APregledRadaGlUrednikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
