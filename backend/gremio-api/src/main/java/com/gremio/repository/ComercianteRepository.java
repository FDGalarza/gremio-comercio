package com.gremio.repository;

import com.gremio.model.Comerciante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ComercianteRepository extends JpaRepository<Comerciante, Long>, JpaSpecificationExecutor<Comerciante> {
    // No necesitas métodos adicionales por ahora
}
