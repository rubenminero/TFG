import { TestBed } from '@angular/core/testing';

import { AthleteguardGuard } from './athleteguard.guard';

describe('AthleteguardGuard', () => {
  let guard: AthleteguardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(AthleteguardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
