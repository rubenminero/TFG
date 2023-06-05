import { TestBed } from '@angular/core/testing';

import { EventsAdminServiceService } from './events-admin-service.service';

describe('EventsAdminServiceService', () => {
  let service: EventsAdminServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventsAdminServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
