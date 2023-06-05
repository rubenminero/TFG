import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarOrganizerComponent } from './navbar-organizer.component';

describe('NavbarOrganizerComponent', () => {
  let component: NavbarOrganizerComponent;
  let fixture: ComponentFixture<NavbarOrganizerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavbarOrganizerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavbarOrganizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
