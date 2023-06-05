import { TestBed } from '@angular/core/testing';

import { TokensAdminServiceService } from './tokens-admin-service.service';

describe('TokensAdminServiceService', () => {
  let service: TokensAdminServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TokensAdminServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
