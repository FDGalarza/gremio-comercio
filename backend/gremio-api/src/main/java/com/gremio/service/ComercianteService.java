package com.gremio.service;

import com.gremio.model.Comerciante;
import com.gremio.repository.ComercianteRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class ComercianteService {

    private final ComercianteRepository comercianteRepository;

    public ComercianteService(ComercianteRepository comercianteRepository) {
        this.comercianteRepository = comercianteRepository;
    }

    // Crear o actualizar comerciante
    public Comerciante guardarComerciante(Comerciante comerciante, String usuarioActualizacion) {
        comerciante.setUsuarioActualizacion(usuarioActualizacion);
        comerciante.setFechaActualizacion(LocalDate.now());

        if (comerciante.getFechaRegistro() == null) {
            comerciante.setFechaRegistro(LocalDate.now());
        }

        return comercianteRepository.save(comerciante);
    }

    // Buscar comerciante por id
    public Optional<Comerciante> buscarPorId(Long id) {
        return comercianteRepository.findById(id);
    }

    // Eliminar comerciante por id
    public void eliminarPorId(Long id) {
        comercianteRepository.deleteById(id);
    }

    // Actualizar estado del comerciante
    public Optional<Comerciante> actualizarEstado(Long id, String nuevoEstado, String usuarioActualizacion) {
        Optional<Comerciante> optionalComerciante = comercianteRepository.findById(id);
        if (optionalComerciante.isPresent()) {
            Comerciante comerciante = optionalComerciante.get();
            comerciante.setEstado(nuevoEstado);
            comerciante.setUsuarioActualizacion(usuarioActualizacion);
            comerciante.setFechaActualizacion(LocalDate.now());
            comercianteRepository.save(comerciante);
            return Optional.of(comerciante);
        }
        return Optional.empty();
    }

    // Consulta paginada con filtros: nombreRazonSocial, fechaRegistro, estado
    public Page<Comerciante> buscarConFiltros(
            String nombreRazonSocial,
            LocalDate fechaRegistro,
            String estado,
            int pagina,
            int tamanio
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanio, Sort.by("idComerciante").ascending());

        Specification<Comerciante> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (nombreRazonSocial != null && !nombreRazonSocial.isBlank()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("nombreRazonSocial")), "%" + nombreRazonSocial.toLowerCase() + "%"));
            }
            if (fechaRegistro != null) {
                predicate = cb.and(predicate, cb.equal(root.get("fechaRegistro"), fechaRegistro));
            }
            if (estado != null && !estado.isBlank()) {
                predicate = cb.and(predicate, cb.equal(root.get("estado"), estado));
            }
            return predicate;
        };

        return comercianteRepository.findAll(spec, pageable);
    }
}
