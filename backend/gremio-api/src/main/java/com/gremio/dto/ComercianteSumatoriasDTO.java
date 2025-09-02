package com.gremio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // genera getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class ComercianteSumatoriasDTO {

    private Long idComerciante;
    private String nombreRazonSocial;
    private String correoElectronico;
    private String municipio;
    private String telefono;
    private String estado;
    private String fechaRegistro;

    private int totalIngresos;
    private int totalEmpleados;
}
