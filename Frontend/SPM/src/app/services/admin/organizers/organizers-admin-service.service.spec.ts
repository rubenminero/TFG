import { TestBed } from '@angular/core/testing';

import { OrganizersAdminServiceService } from './organizers-admin-service.service';

describe('OrganizersAdminServiceService', () => {
  let service: OrganizersAdminServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizersAdminServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
