import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AAutorManjaVecaDoradaComponent } from './a-autor-manja-veca-dorada.component';

describe('AAutorManjaVecaDoradaComponent', () => {
  let component: AAutorManjaVecaDoradaComponent;
  let fixture: ComponentFixture<AAutorManjaVecaDoradaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AAutorManjaVecaDoradaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AAutorManjaVecaDoradaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
