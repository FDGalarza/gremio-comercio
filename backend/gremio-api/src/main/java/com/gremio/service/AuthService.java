package com.gremio.service;

import org.springframework.stereotype.Service;
import com.gremio.repository.UsuarioRepository;
import com.gremio.security.JwtUtil;
import com.gremio.model.Usuario;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
         System.out.println("AuthService inicializado");
    }

    public String login(String correo, String contrasena) {
        System.out.println("Correo recibido AuthService 21: " + correo + ", contraseña: " + contrasena);
        // Buscar usuario por correo
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        System.out.println("Contraseña DB: '" + usuario.getContrasena() + "'");
        System.out.println("Contraseña enviada: '" + contrasena + "'");
        // Validar contraseña
        if (!usuario.getContrasena().equals(contrasena)) {
            throw new RuntimeException("Contraseña incorrecta");
           
        }
        // Generar token JWT
        String token = jwtUtil.generarToken(usuario);
        return token;                                    // <-- Y devolverlo
    }
}
