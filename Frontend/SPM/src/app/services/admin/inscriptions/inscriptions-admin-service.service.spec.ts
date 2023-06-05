import { TestBed } from '@angular/core/testing';

import { InscriptionsAdminServiceService } from './inscriptions-admin-service.service';

describe('InscriptionsAdminServiceService', () => {
  let service: InscriptionsAdminServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InscriptionsAdminServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
