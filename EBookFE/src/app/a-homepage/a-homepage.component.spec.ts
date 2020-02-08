import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AHomepageComponent } from './a-homepage.component';

describe('AHomepageComponent', () => {
  let component: AHomepageComponent;
  let fixture: ComponentFixture<AHomepageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AHomepageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AHomepageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
