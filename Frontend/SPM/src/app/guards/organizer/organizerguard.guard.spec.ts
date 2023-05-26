import { TestBed } from '@angular/core/testing';

import { OrganizerguardGuard } from './organizerguard.guard';

describe('OrganizerguardGuard', () => {
  let guard: OrganizerguardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(OrganizerguardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
