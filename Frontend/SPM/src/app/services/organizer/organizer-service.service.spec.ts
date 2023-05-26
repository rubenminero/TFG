import { TestBed } from '@angular/core/testing';

import { OrganizerServiceService } from './organizer-service.service';

describe('OrganizerServiceService', () => {
  let service: OrganizerServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizerServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
