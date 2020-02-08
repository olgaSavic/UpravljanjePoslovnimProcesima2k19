import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AHomepageListaTaskovaComponent } from './a-homepage-lista-taskova.component';

describe('AHomepageListaTaskovaComponent', () => {
  let component: AHomepageListaTaskovaComponent;
  let fixture: ComponentFixture<AHomepageListaTaskovaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AHomepageListaTaskovaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AHomepageListaTaskovaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
