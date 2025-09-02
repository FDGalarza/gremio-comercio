import { TestBed } from '@angular/core/testing';

import { ComerciantesService } from './comerciantes.services';

describe('Comerciantes', () => {
  let service: ComerciantesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComerciantesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
