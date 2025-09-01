package com.gremio.controller;

import com.gremio.service.AuthService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

// Controlador para manejar la autenticación
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (CORS)
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint público para login
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        System.out.println("login authController");
        try {
        String token = authService.login(request.getCorreoElectronico(), request.getContrasena());
        return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

// Clase para recibir los datos del login
@Data
class LoginRequest {
    private String correoElectronico;
    private String contrasena;
}

// Clase para retornar el token JWT
@Data
@AllArgsConstructor
class JwtResponse {
    private final String token;
}

