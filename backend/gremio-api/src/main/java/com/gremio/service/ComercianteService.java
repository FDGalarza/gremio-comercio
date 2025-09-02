package com.gremio.service;

import com.gremio.model.Comerciante;
import com.gremio.repository.ComercianteRepository;
import com.gremio.dto.ComercianteSumatoriasDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class ComercianteService {

    private final ComercianteRepository comercianteRepository;

    //Simula los establecimientos con ingresos y empleados por comerciante
    private final Map<Long, List<Map<String, Integer>>> dataSemilla = Map.of(
        1L, List.of(Map.of("ingresos", 5000, "cantidadEmpleados", 3)),
        2L, List.of(Map.of("ingresos", 12000, "cantidadEmpleados", 5))
    );

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

    public ComercianteSumatoriasDTO obtenerSumatoriasPorComerciante(Long idComerciante) {
        // Obtener el comerciante de la base de datos
        Optional<Comerciante> comercianteOpt = buscarPorId(idComerciante);
        if (comercianteOpt.isEmpty()) {
            return null; // o lanza excepción según tu manejo de errores
        }

        Comerciante comerciante = comercianteOpt.get();

        // Crear el DTO y mapear los campos
        ComercianteSumatoriasDTO dto = new ComercianteSumatoriasDTO();
        dto.setIdComerciante(comerciante.getIdComerciante());
        dto.setNombreRazonSocial(comerciante.getNombreRazonSocial());
        dto.setCorreoElectronico(comerciante.getCorreoElectronico());
        dto.setMunicipio(comerciante.getMunicipio());
        dto.setTelefono(comerciante.getTelefono());
        dto.setEstado(comerciante.getEstado());
        // Aquí conviertes LocalDate a String
        dto.setFechaRegistro(comerciante.getFechaRegistro().toString());

        // Cargar sumatorias desde dataSemilla
        List<Map<String, Integer>> establecimientos = dataSemilla.getOrDefault(idComerciante, List.of());
        int totalIngresos = establecimientos.stream().mapToInt(e -> e.get("ingresos")).sum();
        int totalEmpleados = establecimientos.stream().mapToInt(e -> e.get("cantidadEmpleados")).sum();

        dto.setTotalIngresos(totalIngresos);
        dto.setTotalEmpleados(totalEmpleados);

        return dto;
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

    public ComercianteSumatoriasDTO convertirADTO(Comerciante comerciante) {
        ComercianteSumatoriasDTO dto = new ComercianteSumatoriasDTO();
        dto.setIdComerciante(comerciante.getIdComerciante());
        dto.setNombreRazonSocial(comerciante.getNombreRazonSocial());
        dto.setFechaRegistro(comerciante.getFechaRegistro().toString()); // convierte LocalDate a String

        // Suponiendo que tienes dataSemilla cargada
        List<Map<String, Integer>> establecimientos = dataSemilla.getOrDefault(comerciante.getIdComerciante(), new ArrayList<>());
        int totalIngresos = establecimientos.stream().mapToInt(e -> e.get("ingresos")).sum();
        int totalEmpleados = establecimientos.stream().mapToInt(e -> e.get("cantidadEmpleados")).sum();

        dto.setTotalIngresos(totalIngresos);
        dto.setTotalEmpleados(totalEmpleados);

        return dto;
    }
}
