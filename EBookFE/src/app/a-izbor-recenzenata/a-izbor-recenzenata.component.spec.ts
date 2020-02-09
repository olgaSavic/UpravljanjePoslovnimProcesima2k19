import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AIzborRecenzenataComponent } from './a-izbor-recenzenata.component';

describe('AIzborRecenzenataComponent', () => {
  let component: AIzborRecenzenataComponent;
  let fixture: ComponentFixture<AIzborRecenzenataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AIzborRecenzenataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AIzborRecenzenataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
