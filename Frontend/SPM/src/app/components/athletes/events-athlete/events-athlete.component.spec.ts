import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventsAthleteComponent } from './events-athlete.component';

describe('EventsComponent', () => {
  let component: EventsAthleteComponent;
  let fixture: ComponentFixture<EventsAthleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EventsAthleteComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EventsAthleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
