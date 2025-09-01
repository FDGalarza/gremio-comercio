package com.gremio.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro JWT que se ejecuta una vez por cada petición HTTP.
 * Valida el token en la cabecera Authorization y setea la autenticación.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        String path = request.getServletPath();

        // Ignorar todas las rutas de autenticación y solicitudes OPTIONS
        if (path.startsWith("/api/auth/") || request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener token de la cabecera Authorization
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                // Validar token y extraer información
                Claims claims = jwtUtil.validarToken(token); // Método de JwtUtil que retorna Claims
                String correo = claims.getSubject();
                String rol = claims.get("rol", String.class);  // <--- Recupera el rol del token

                if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Crear la autoridad con el prefijo ROLE_
                    String roleName = rol.startsWith("ROLE_") ? rol : "ROLE_" + rol;
                    var authorities = Collections.singletonList(new SimpleGrantedAuthority(roleName));

                    // Crear autenticación y setear en el contexto de seguridad
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(correo, null, authorities);

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            } catch (Exception e) {
                // Si el token no es válido, limpiar contexto y continuar
                SecurityContextHolder.clearContext();
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
