import { TestBed } from '@angular/core/testing';

import { AthletesAdminServiceService } from './athletes-admin-service.service';

describe('AthletesAdminServiceService', () => {
  let service: AthletesAdminServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AthletesAdminServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
