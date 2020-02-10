import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { APlacanjeClanarineComponent } from './a-placanje-clanarine.component';

describe('APlacanjeClanarineComponent', () => {
  let component: APlacanjeClanarineComponent;
  let fixture: ComponentFixture<APlacanjeClanarineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ APlacanjeClanarineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(APlacanjeClanarineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
