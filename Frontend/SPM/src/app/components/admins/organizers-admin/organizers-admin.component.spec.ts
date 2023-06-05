import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizersAdminComponent } from './organizers-admin.component';

describe('OrganizersAdminComponent', () => {
  let component: OrganizersAdminComponent;
  let fixture: ComponentFixture<OrganizersAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrganizersAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizersAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
