import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizersMenuComponent } from './organizers-menu.component';

describe('OrganizersMenuComponent', () => {
  let component: OrganizersMenuComponent;
  let fixture: ComponentFixture<OrganizersMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrganizersMenuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizersMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
