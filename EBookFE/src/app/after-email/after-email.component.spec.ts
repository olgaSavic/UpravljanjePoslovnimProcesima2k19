import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AfterEmailComponent } from './after-email.component';

describe('AfterEmailComponent', () => {
  let component: AfterEmailComponent;
  let fixture: ComponentFixture<AfterEmailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AfterEmailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AfterEmailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
