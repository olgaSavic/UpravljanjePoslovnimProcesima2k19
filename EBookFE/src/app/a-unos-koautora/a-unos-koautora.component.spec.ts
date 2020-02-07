import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AUnosKoautoraComponent } from './a-unos-koautora.component';

describe('AUnosKoautoraComponent', () => {
  let component: AUnosKoautoraComponent;
  let fixture: ComponentFixture<AUnosKoautoraComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AUnosKoautoraComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AUnosKoautoraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
