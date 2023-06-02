import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentsOrganizerComponent } from './tournaments-organizer.component';

describe('TournamentsOrganizerComponent', () => {
  let component: TournamentsOrganizerComponent;
  let fixture: ComponentFixture<TournamentsOrganizerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TournamentsOrganizerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TournamentsOrganizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
