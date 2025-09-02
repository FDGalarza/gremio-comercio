import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/pages/login/login.component';
import { HomeComponent } from './features/comerciantes/pages/home/home.component';
import { CrearComercianteComponent } from './features/comerciantes/pages/crear-comerciante/crear-comerciante.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent }, // Ruta al Home después del login
  { path: 'comerciantes/crear', component: CrearComercianteComponent },
  { path: 'comerciantes/:idComerciante/editar', component: CrearComercianteComponent },
  { path: '**', redirectTo: 'login' } // Ruta comodín
];
