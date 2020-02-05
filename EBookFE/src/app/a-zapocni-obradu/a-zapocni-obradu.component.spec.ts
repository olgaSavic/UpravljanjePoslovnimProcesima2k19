import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AZapocniObraduComponent } from './a-zapocni-obradu.component';

describe('AZapocniObraduComponent', () => {
  let component: AZapocniObraduComponent;
  let fixture: ComponentFixture<AZapocniObraduComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AZapocniObraduComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AZapocniObraduComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
