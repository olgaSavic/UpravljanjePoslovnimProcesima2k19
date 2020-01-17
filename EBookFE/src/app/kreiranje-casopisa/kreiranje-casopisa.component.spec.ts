import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KreiranjeCasopisaComponent } from './kreiranje-casopisa.component';

describe('KreiranjeCasopisaComponent', () => {
  let component: KreiranjeCasopisaComponent;
  let fixture: ComponentFixture<KreiranjeCasopisaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KreiranjeCasopisaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KreiranjeCasopisaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
