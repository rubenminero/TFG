import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventsOrganizerComponent } from './events-organizer.component';

describe('EventsOrganizerComponent', () => {
  let component: EventsOrganizerComponent;
  let fixture: ComponentFixture<EventsOrganizerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EventsOrganizerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventsOrganizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
