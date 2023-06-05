import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentsAthleteComponent } from './tournaments-athlete.component';

describe('TournamentsComponent', () => {
  let component: TournamentsAthleteComponent;
  let fixture: ComponentFixture<TournamentsAthleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TournamentsAthleteComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentsAthleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
