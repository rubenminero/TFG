import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarAthleteComponent } from './navbar-athlete.component';

describe('AthletesMenuComponent', () => {
  let component: NavbarAthleteComponent;
  let fixture: ComponentFixture<NavbarAthleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavbarAthleteComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavbarAthleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
