import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterEventOrganizerComponent } from './register-event-organizer.component';

describe('RegisterEventOrganizerComponent', () => {
  let component: RegisterEventOrganizerComponent;
  let fixture: ComponentFixture<RegisterEventOrganizerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterEventOrganizerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterEventOrganizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
