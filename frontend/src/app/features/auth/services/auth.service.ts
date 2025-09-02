import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface JwtResponse {
  token: string;
}

interface LoginRequest {
  correoElectronico: string;
  contrasena: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = "http://localhost:8080/api/auth";

  constructor(private http: HttpClient){ }

  login(correoElectronico: string, contrasena: string): Observable<JwtResponse>{
    const body: LoginRequest = {correoElectronico, contrasena};
    return this.http.post<JwtResponse>(`${this.baseUrl}/login`, body);
  }

  logout(): void{
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
     return !!this.getToken();
  }
  
}
