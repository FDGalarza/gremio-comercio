import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Comerciante {
  idComerciante: number;
  nombreRazonSocial: string;
  municipio?: string;
  telefono?: string | null;
  correoElectronico?: string | null;
  fechaRegistro: string;
  estado: string;
  fechaActualizacion?: string;
  usuarioActualizacion?: string;
  totalIngresos?: number;
  totalEmpleados?: number;
}

interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

export interface Municipio {
  id: number;
  nombre: string;
}

@Injectable({
  providedIn: 'root'
})
export class ComerciantesService {
  private apiUrl = 'http://localhost:8080/api/comerciantes';

  constructor(private http: HttpClient) {}

  listar(nombre?: string, fecha?: string, estado?: string, pagina = 0, tamanio = 5): Observable<any> {
    let params = new HttpParams()
      .set('pagina', pagina)
      .set('tamanio', tamanio);

    const token = localStorage.getItem('token'); // obtener el token
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    if (nombre) params = params.set('nombreRazonSocial', nombre);
    if (fecha) params = params.set('fechaRegistro', fecha);
    if (estado) params = params.set('estado', estado);
    console.log("Service comerciante 33 "+this.apiUrl);
    return this.http.get<any>(this.apiUrl, { params, headers });
  }

  crear(comerciante: Comerciante) {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post(`${this.apiUrl}`, comerciante, { headers });
  }

  // Nuevo método para obtener comerciante por ID
  obtenerPorId(id: number): Observable<ApiResponse<Comerciante>> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<ApiResponse<Comerciante>>(`${this.apiUrl}/${id}`, { headers });
  }

  // Nuevo método para actualizar comerciante
  actualizar(id: number, comerciante: Comerciante): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put(`${this.apiUrl}/${id}`, comerciante, { headers });
  }

  eliminar(id: number): Observable<any> {
    const token = localStorage.getItem('token') || '';
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete(`${this.apiUrl}/${id}`, { headers });
  }

  /**
 * Obtener lista completa de municipios
 */
  obtenerMunicipios(): Observable<ApiResponse<Municipio[]>> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<ApiResponse<Municipio[]>>(`http://localhost:8080/api/municipios`, { headers });
  }

  /**
 * Obtener todos los establecimientos asociados a un comerciante
 */
  obtenerEstablecimientosPorComerciante(idComerciante: number): Observable<any[]> {
    const token = localStorage.getItem('token') || '';
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const url = `http://localhost:8080/api/${idComerciante}/establecimientos`; // Ajusta la URL según tu backend
    return this.http.get<any[]>(url, { headers });
  }

  cambiarEstado(id: number, estado: 'ACTIVO' | 'INACTIVO'): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    console.log("estado 99: ",estado);
    return this.http.patch(`${this.apiUrl}/${id}/estado`, {}, {
      params: new HttpParams().set('estado', estado),
      headers
    });
  }

  descargarReporte(): Observable<Blob> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get(`http://localhost:8080/api/reportes/comerciantes-activos/csv`, {
      headers,
      responseType: 'blob' // clave para archivos
    });
  }
  
}