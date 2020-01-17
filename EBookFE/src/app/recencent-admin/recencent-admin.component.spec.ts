import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecencentAdminComponent } from './recencent-admin.component';

describe('RecencentAdminComponent', () => {
  let component: RecencentAdminComponent;
  let fixture: ComponentFixture<RecencentAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecencentAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecencentAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
