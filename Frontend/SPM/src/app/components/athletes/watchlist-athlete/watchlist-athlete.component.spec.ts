import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WatchlistAthleteComponent } from './watchlist-athlete.component';

describe('WatchlistAthleteComponent', () => {
  let component: WatchlistAthleteComponent;
  let fixture: ComponentFixture<WatchlistAthleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WatchlistAthleteComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(WatchlistAthleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
