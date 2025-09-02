import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ComerciantesService, Comerciante } from '../../services/comerciantes.services';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-home',
  standalone: true, // <- obligatorio para standalone
  imports: [CommonModule, FormsModule, RouterModule], // <- aquí van los módulos que necesita
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  comerciantes: Comerciante[] = [];
  pagina = 0;
  tamanio = 5;
  totalElementos = 0;
  cargando = false;
  errorMsg = '';
  esAdmin = true;
  totalPaginas = 0; 
  

  constructor(private comerciantesService: ComerciantesService, private router: Router,) {}

  ngOnInit(): void {
    this.cargarComerciantes();
  }

  cargarComerciantes(): void {
    this.cargando = true;
    this.errorMsg = '';
    const token = localStorage.getItem('token'); // obtiene el token
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.comerciantesService.listar(undefined, undefined, undefined, this.pagina, this.tamanio)
      .subscribe({
        next: (res) => {
          console.log('Respuesta API:', res);
          this.comerciantes = res.data.content;
          this.totalElementos = res.data.totalElements;
          this.cargando = false;
        },
        error: (err) => {
          this.errorMsg = 'Ocurrió un error al cargar los comerciantes.';
          this.cargando = false;
        }
      });
  }

  cambiarEstado(comerciante: Comerciante): void {
    const nuevoEstado = comerciante.estado === 'ACTIVO' ? 'INACTIVO' : 'ACTIVO';
    this.comerciantesService.cambiarEstado(comerciante.idComerciante, nuevoEstado)
      .subscribe(() => this.cargarComerciantes());
  }

  eliminar(id: number): void {
    if (confirm('¿Está seguro de eliminar este comerciante?')) {
      this.comerciantesService.eliminar(id)
        .subscribe(() => this.cargarComerciantes());
    }
  }

  descargarCSV(): void {
    this.comerciantesService.descargarReporte()
      .subscribe((blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'reporte_comerciantes.csv';
        a.click();
        window.URL.revokeObjectURL(url);
      });
  }

  cerrarSesion() {
    // Elimina token o datos del usuario del localStorage
    localStorage.removeItem('token');
    localStorage.removeItem('usuario'); // si guardas el nombre o rol del usuario

    // Redirige al login
    this.router.navigate(['/login']);
  }

  toggleEstado(comerciante: any) {
    const nuevoEstado: 'ACTIVO' | 'INACTIVO' = 
        comerciante.estado === 'ACTIVO' ? 'INACTIVO' : 'ACTIVO';

    this.comerciantesService.cambiarEstado(comerciante.idComerciante, nuevoEstado)
      .subscribe({
        next: () => {
          comerciante.estado = nuevoEstado; // Actualiza la UI automáticamente
        },
        error: (err) => console.error('Error al cambiar estado:', err)
      });
  }
}
