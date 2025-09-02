import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [ CommonModule, ReactiveFormsModule]
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      contrasena: ['', Validators.required],
      aceptarTerminos: [false, Validators.requiredTrue]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      
      const { email, contrasena } = this.loginForm.value;
      console.log(email);
      console.log(contrasena);
      this.authService.login(email, contrasena).subscribe({
        next: (res) => {
          localStorage.setItem('token', res.token);
          this.router.navigate(['/home']); // 
        },
        error: () => {
          alert('Usuario o contraseña incorrectos');
        }
      });
    }
  }
}
