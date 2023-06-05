import { TestBed } from '@angular/core/testing';

import { WatchlistsAdminServiceService } from './watchlists-admin-service.service';

describe('WatchlistsAdminServiceService', () => {
  let service: WatchlistsAdminServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WatchlistsAdminServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
