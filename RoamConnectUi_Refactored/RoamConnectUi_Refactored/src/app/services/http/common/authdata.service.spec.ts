import { TestBed, inject } from '@angular/core/testing';

import { AuthdataService } from './authdata.service';

describe('AuthdataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AuthdataService]
    });
  });

  it('should ...', inject([AuthdataService], (service: AuthdataService) => {
    expect(service).toBeTruthy();
  }));
});
