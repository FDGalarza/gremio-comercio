import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ComerciantesService, Comerciante, Municipio } from '../../services/comerciantes.services';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-crear-comerciante',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './crear-comerciante.component.html',
  styleUrls: ['./crear-comerciante.component.css']
})
export class CrearComercianteComponent implements OnInit {
  form: FormGroup;
  errorMsg = '';
  idComerciante?: number;
  modoEdicion = false;
  totalIngresos: number = 0;
  totalEmpleados: number = 0;
  municipios: Municipio[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private comerciantesService: ComerciantesService
  ) {
    this.form = this.fb.group({
      nombreRazonSocial: ['', [Validators.required, Validators.maxLength(100)]],
      correoElectronico: ['', [Validators.email]],
      estado: ['', Validators.required],
      municipio: ['', Validators.required],
      telefono: ['', Validators.pattern(/^[0-9]{7,10}$/)],
      fechaRegistro: [new Date(), Validators.required],
      poseeEstablecimientos: [false]
    });
  }

  

  ngOnInit(): void {
    this.idComerciante = Number(this.route.snapshot.paramMap.get('idComerciante'));
    this.cargarMunicipios();
    if (this.idComerciante) {
      this.modoEdicion = true;
      this.cargarComerciante(this.idComerciante);
    }
  }

  cargarMunicipios() {
    this.comerciantesService.obtenerMunicipios().subscribe({
      next: (res) => this.municipios = res.data, // ✅ SOLO el array dentro de data
      error: (err) => console.error('Error al cargar municipios:', err)
    });
  }

  cargarComerciante(id: number) {
  this.comerciantesService.obtenerPorId(id).subscribe({
      next: (response) => {
        const comerciante = response.data;
        console.log('Respuesta completa:', comerciante);
        
        // Rellenar formulario con datos básicos
        // Rellenar formulario campo a campo
        this.form.patchValue({
          nombreRazonSocial: comerciante.nombreRazonSocial ?? '',
          correoElectronico: comerciante.correoElectronico ?? '',
          estado: comerciante.estado ?? '',
          municipio: comerciante.municipio ?? '',
          telefono: comerciante.telefono ?? '',
          fechaRegistro: comerciante.fechaRegistro
            ? new Date(comerciante.fechaRegistro)
            : new Date()
        });

        // Asignar sumatorias
        this.totalIngresos = comerciante.totalEmpleados ?? 0;
        this.totalEmpleados = comerciante.totalEmpleados ?? 0;
      },
      error: (err) => {
        console.error('Error al cargar comerciante:', err);
        this.errorMsg = 'Error al cargar comerciante';
      }
    });
  }

  cargarSumatorias(id: number) {
  this.comerciantesService.obtenerEstablecimientosPorComerciante(id).subscribe({
      next: (data: any[]) => {
        this.totalIngresos = data.reduce((sum, e) => sum + e.ingresos, 0);
        this.totalEmpleados = data.reduce((sum, e) => sum + e.cantidadEmpleados, 0);
      },
      error: (err) => console.error('Error al cargar sumatorias:', err)
    });
  }

  onSubmit() {
    if (this.form.invalid) return;

    const comerciante: Comerciante = this.form.value;

    if (this.modoEdicion && this.idComerciante) {
      this.comerciantesService.actualizar(this.idComerciante, comerciante).subscribe({
        next: () => this.router.navigate(['/home']),
        error: () => this.errorMsg = 'Error al actualizar comerciante'
        
      });
    } else {
      this.comerciantesService.crear(comerciante).subscribe({
        next: () => this.router.navigate(['/home']),
        error: () => this.errorMsg = 'Error al crear comerciante'
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/home']);
  }
}