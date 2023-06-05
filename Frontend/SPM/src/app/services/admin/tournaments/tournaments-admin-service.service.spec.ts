import { TestBed } from '@angular/core/testing';

import { TournamentsAdminServiceService } from './tournaments-admin-service.service';

describe('TournamentsAdminServiceService', () => {
  let service: TournamentsAdminServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TournamentsAdminServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
