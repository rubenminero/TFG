import { TestBed } from '@angular/core/testing';

import { SportsTypesAdminServiceService } from './sport-types-admin.service';

describe('SportTypesService', () => {
  let service: SportsTypesAdminServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SportsTypesAdminServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
