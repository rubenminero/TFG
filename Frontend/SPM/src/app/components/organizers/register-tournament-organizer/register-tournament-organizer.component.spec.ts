import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterTournamentOrganizerComponent } from './register-tournament-organizer.component';

describe('RegisterTournamentOrganizerComponent', () => {
  let component: RegisterTournamentOrganizerComponent;
  let fixture: ComponentFixture<RegisterTournamentOrganizerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterTournamentOrganizerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterTournamentOrganizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
