import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteAllDisabledAdminComponent } from './delete-all-disabled-admin.component';

describe('DeleteAllDisabledAdminComponent', () => {
  let component: DeleteAllDisabledAdminComponent;
  let fixture: ComponentFixture<DeleteAllDisabledAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteAllDisabledAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteAllDisabledAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
