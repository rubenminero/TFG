import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InscriptionsAthleteComponent } from './inscriptions-athlete.component';

describe('InscriptionsComponent', () => {
  let component: InscriptionsAthleteComponent;
  let fixture: ComponentFixture<InscriptionsAthleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InscriptionsAthleteComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(InscriptionsAthleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
