import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';      // <- necesario para [(ngModel)]
import { RouterModule } from '@angular/router';    // <- necesario para routerLink
import { HomeComponent } from './pages/home/home.component';

@NgModule({
  declarations: [
    
  ],
  imports: [
    CommonModule, // <- necesario para *ngIf, *ngFor y pipes como date
    FormsModule,  // <- necesario para [(ngModel)]
    RouterModule,  // <- necesario para routerLink
    HomeComponent // <- declara tu componente Home
  ],
  exports: [
    HomeComponent // <- si quieres usar Home fuera del módulo
  ]
})
export class ComerciantesModule { }
