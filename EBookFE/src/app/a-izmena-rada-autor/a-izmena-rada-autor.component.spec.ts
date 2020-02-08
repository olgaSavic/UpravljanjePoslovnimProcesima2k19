import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AIzmenaRadaAutorComponent } from './a-izmena-rada-autor.component';

describe('AIzmenaRadaAutorComponent', () => {
  let component: AIzmenaRadaAutorComponent;
  let fixture: ComponentFixture<AIzmenaRadaAutorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AIzmenaRadaAutorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AIzmenaRadaAutorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
