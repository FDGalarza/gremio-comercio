package com.gremio.controller;

import com.gremio.api.ApiResponse;
import com.gremio.model.Municipio;
import com.gremio.service.MunicipioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para manejar los endpoints relacionados con MUNICIPIOS.
 * Este endpoint es PRIVADO y protegido con JWT.
 * Roles permitidos: ADMIN y AUXILIAR DE REGISTRO.
 */
@RestController
@RequestMapping("/api/municipios")
@AllArgsConstructor
public class MunicipioController {

    private final MunicipioService municipioService;

    /**
     * GET /api/municipios
     * Obtiene la lista completa de municipios (con cache habilitada).
     * Solo pueden acceder ADMIN y AUXILIAR.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AUXILIAR_REGISTRO')")
    public ResponseEntity<ApiResponse<List<Municipio>>> obtenerMunicipios() {
        List<Municipio> municipios = municipioService.obtenerMunicipios();
        return ResponseEntity.ok(ApiResponse.ok("Lista de municipios obtenida", municipios));
    }

    /**
     * DELETE /api/municipios/cache
     * Limpia manualmente la cache de municipios.
     * Solo ADMIN puede ejecutar este endpoint.
     */
    @DeleteMapping("/cache")
    @PreAuthorize("hasRole('ADMINISTRADOR')")//Controla que solo roles autorizados puedan consumir el endpoint.
    public ResponseEntity<ApiResponse<Void>> limpiarCache() {
        municipioService.limpiarCache();
        return ResponseEntity.ok(ApiResponse.ok("Cache de municipios limpiada", null));
    }
}
