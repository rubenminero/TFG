import { TestBed } from '@angular/core/testing';

import { AthleteGuard } from './athlete.guard';

describe('AthleteGuard', () => {
  let guard: AthleteGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(AthleteGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
