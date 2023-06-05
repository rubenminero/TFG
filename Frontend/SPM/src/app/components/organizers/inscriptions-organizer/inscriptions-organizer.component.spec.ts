import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InscriptionsOrganizerComponent } from './inscriptions-organizer.component';

describe('InscriptionsOrganizerComponent', () => {
  let component: InscriptionsOrganizerComponent;
  let fixture: ComponentFixture<InscriptionsOrganizerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InscriptionsOrganizerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InscriptionsOrganizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
