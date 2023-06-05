import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminsMenuComponent } from './admins-menu.component';

describe('AdminsMenuComponent', () => {
  let component: AdminsMenuComponent;
  let fixture: ComponentFixture<AdminsMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminsMenuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminsMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
