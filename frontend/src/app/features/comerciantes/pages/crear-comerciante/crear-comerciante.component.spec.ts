import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearComerciante } from './crear-comerciante';

describe('CrearComerciante', () => {
  let component: CrearComerciante;
  let fixture: ComponentFixture<CrearComerciante>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearComerciante]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearComerciante);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
