package com.gremio.security;

import com.gremio.model.Usuario;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

// Clase para generar y validar tokens JWT
@Component
public class JwtUtil {

    // Cargamos valores desde application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationMs;

    // Genera un token JWT con datos del usuario
    public String generarToken(Usuario usuario) {
    try {
            System.out.println("Entrando a generarToken con usuario 19: " + usuario.getCorreoElectronico());

            Date ahora = new Date();
            Date expiracion = new Date(ahora.getTime() + expirationMs);

            String token = Jwts.builder()
                    .setSubject(usuario.getCorreoElectronico())
                    .claim("rol", usuario.getRol().name())  // <--- Guardamos el rol en el token
                    .setIssuedAt(ahora)
                    .setExpiration(expiracion)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();

            System.out.println("Token creado correctamente");
            return token;

        } catch (Exception e) {
            System.err.println("Error en generarToken: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Valida el token y retorna los datos (claims)
    public Claims validarToken(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)                  // Usa la misma clave para validar
            .parseClaimsJws(token)                      // Parsea el token
            .getBody();                                 // Retorna los datos del token
    }
}
