import { TestBed } from '@angular/core/testing';

import { AthleteServiceService } from './athlete-service.service';

describe('AthleteServiceService', () => {
  let service: AthleteServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AthleteServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
