package com.gremio.security;

import com.gremio.model.Usuario;
import com.gremio.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Este servicio le dice a Spring cómo cargar usuarios y roles desde la base de datos.
 * Es fundamental para que @PreAuthorize reconozca bien los roles al validar el token.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Prefijo "ROLE_" obligatorio para que Spring reconozca el rol
        String roleName = "ROLE_" + usuario.getRol().name();
        System.out.println("Asignando rol al usuario: " + roleName);
        System.out.println("Asignando rol al usuario: " + roleName);

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getCorreoElectronico())
                .password(usuario.getContrasena())
                .authorities(List.of(new SimpleGrantedAuthority(roleName)))
                .build();
    }
}
