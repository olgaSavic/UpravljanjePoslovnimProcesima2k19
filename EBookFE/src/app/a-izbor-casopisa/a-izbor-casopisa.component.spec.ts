import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AIzborCasopisaComponent } from './a-izbor-casopisa.component';

describe('AIzborCasopisaComponent', () => {
  let component: AIzborCasopisaComponent;
  let fixture: ComponentFixture<AIzborCasopisaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AIzborCasopisaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AIzborCasopisaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
