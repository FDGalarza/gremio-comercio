package com.gremio.repository;
import com.gremio.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
}
