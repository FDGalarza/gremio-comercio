package com.gremio.controller;

import com.gremio.dto.ComercianteReporteDTO;
import com.gremio.repository.ComercianteReporteRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteComercianteController {

    private final ComercianteReporteRepository comercianteReporteRepository;

    public ReporteComercianteController(ComercianteReporteRepository comercianteReporteRepository) {
        this.comercianteReporteRepository = comercianteReporteRepository;
    }

    @GetMapping("/comerciantes-activos/csv")
    @PreAuthorize("hasRole('ADMINISTRADOR')") // Solo admins
    public void descargarReporteComerciantesActivos(HttpServletResponse response) throws IOException {
        // Obtener comerciantes activos
        List<ComercianteReporteDTO> comerciantes = comercianteReporteRepository.obtenerComerciantesActivos();

        // Configurar la respuesta HTTP para descarga
        String filename = "comerciantes_activos.csv";
        response.setContentType("text/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + java.net.URLEncoder.encode(filename, "UTF-8"));

        try (PrintWriter writer = response.getWriter()) {
            // Cabecera del CSV
            writer.println("Nombre|Municipio|Telefono|CorreoElectronico|FechaRegistro|Estado|CantidadEstablecimientos|TotalIngresos|CantidadEmpleados");

            // Filas del CSV
            for (ComercianteReporteDTO c : comerciantes) {
                writer.printf("%s|%s|%s|%s|%s|%s|%d|%.2f|%d%n",
                        c.getNombreRazonSocial(),
                        c.getMunicipio(),
                        c.getTelefono(),
                        c.getCorreoElectronico(),
                        c.getFechaRegistro(),
                        c.getEstado(),
                        c.getCantidadEstablecimientos(),
                        c.getTotalIngresos(),
                        c.getCantidadEmpleados()
                );
            }
            writer.flush();
        }
    }
}
