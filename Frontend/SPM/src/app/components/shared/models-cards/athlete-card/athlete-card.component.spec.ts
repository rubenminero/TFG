import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteCardComponent } from './athlete-card.component';

describe('AthleteCardComponent', () => {
  let component: AthleteCardComponent;
  let fixture: ComponentFixture<AthleteCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AthleteCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
