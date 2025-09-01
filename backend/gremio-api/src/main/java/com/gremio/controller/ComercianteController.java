package com.gremio.controller;

import com.gremio.api.ApiResponse;
import com.gremio.model.Comerciante;
import com.gremio.service.ComercianteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api/comerciantes")
public class ComercianteController {

    private final ComercianteService comercianteService;

    public ComercianteController(ComercianteService comercianteService) {
        this.comercianteService = comercianteService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Comerciante>>> listarComerciantes(
            @RequestParam(required = false) String nombreRazonSocial,
            @RequestParam(required = false) String fechaRegistro,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamanio
    ) {
        LocalDate fecha = null;
        if (fechaRegistro != null && !fechaRegistro.isBlank()) {
            try {
                fecha = LocalDate.parse(fechaRegistro);
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.fail("Formato de fecha inválido. Use yyyy-MM-dd"));
            }
        }

        Page<Comerciante> resultado = comercianteService.buscarConFiltros(nombreRazonSocial, fecha, estado, pagina, tamanio);
        return ResponseEntity.ok(ApiResponse.ok("Consulta exitosa", resultado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Comerciante>> obtenerPorId(@PathVariable Long id) {
        Optional<Comerciante> comerciante = comercianteService.buscarPorId(id);
        if (comerciante.isPresent()) {
            return ResponseEntity.ok(ApiResponse.ok("Comerciante encontrado", comerciante.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("Comerciante no encontrado"));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<Comerciante>> crearComerciante(
            @Valid @RequestBody Comerciante comerciante,
            Principal principal
    ) {
        Comerciante creado = comercianteService.guardarComerciante(comerciante, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Comerciante creado", creado));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<Comerciante>> actualizarComerciante(
            @PathVariable Long id,
            @Valid @RequestBody Comerciante comerciante,
            Principal principal
    ) {
        Optional<Comerciante> existente = comercianteService.buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("Comerciante no encontrado"));
        }
        comerciante.setIdComerciante(id);
        Comerciante actualizado = comercianteService.guardarComerciante(comerciante, principal.getName());
        return ResponseEntity.ok(ApiResponse.ok("Comerciante actualizado", actualizado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<Void>> eliminarComerciante(@PathVariable Long id) {
        Optional<Comerciante> existente = comercianteService.buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("Comerciante no encontrado"));
        }
        comercianteService.eliminarPorId(id);
        return ResponseEntity.ok(ApiResponse.ok("Comerciante eliminado", null));
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<Comerciante>> modificarEstado(
            @PathVariable Long id,
            @RequestParam String estado,
            Principal principal
    ) {
        if (!estado.equals("ACTIVO") && !estado.equals("INACTIVO")) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail("Estado inválido. Debe ser ACTIVO o INACTIVO"));
        }
        Optional<Comerciante> actualizado = comercianteService.actualizarEstado(id, estado, principal.getName());
        if (actualizado.isPresent()) {
            return ResponseEntity.ok(ApiResponse.ok("Estado actualizado", actualizado.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("Comerciante no encontrado"));
        }
    }
}
