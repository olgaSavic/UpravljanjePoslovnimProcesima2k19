import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { APregledPdfGlUrednikComponent } from './a-pregled-pdf-gl-urednik.component';

describe('APregledPdfGlUrednikComponent', () => {
  let component: APregledPdfGlUrednikComponent;
  let fixture: ComponentFixture<APregledPdfGlUrednikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ APregledPdfGlUrednikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(APregledPdfGlUrednikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
