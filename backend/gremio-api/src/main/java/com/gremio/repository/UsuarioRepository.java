package com.gremio.repository;

import com.gremio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repositorio JPA para la entidad Usuario
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar usuario por correo electrónico
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
}
