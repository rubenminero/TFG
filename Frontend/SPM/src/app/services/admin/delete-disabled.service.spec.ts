import { TestBed } from '@angular/core/testing';

import { DeleteDisabledService } from './delete-disabled.service';

describe('DeleteDisabledService', () => {
  let service: DeleteDisabledService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeleteDisabledService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
