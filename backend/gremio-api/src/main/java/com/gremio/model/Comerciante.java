package com.gremio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "comerciante")
public class Comerciante {

    @Id
    @Column(name = "ID_COMERCIANTE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_comerciante")
    @SequenceGenerator(name = "seq_comerciante", sequenceName = "seq_comerciante", allocationSize = 1)
    private Long idComerciante;

    @NotBlank(message = "El nombre o razón social es obligatorio")
    @Size(max = 150)
    @Column(name = "NOMBRE_RAZON_SOCIAL", nullable = false, length = 150)
    private String nombreRazonSocial;

    @NotBlank(message = "El municipio es obligatorio")
    @Size(max = 100)
    @Column(name = "MUNICIPIO", nullable = false, length = 100)
    private String municipio;

    @Size(max = 20)
    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    @Email(message = "El correo electrónico debe tener un formato válido")
    @Size(max = 150)
    @Column(name = "CORREO_ELECTRONICO", length = 150)
    private String correoElectronico;

    @NotNull(message = "La fecha de registro es obligatoria")
    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDate fechaRegistro;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO", message = "El estado debe ser ACTIVO o INACTIVO")
    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDate fechaActualizacion;

    @Size(max = 100)
    @Column(name = "USUARIO_ACTUALIZACION", length = 100)
    private String usuarioActualizacion;

    // Getters y setters

    public Long getIdComerciante() {
        return idComerciante;
    }

    public void setIdComerciante(Long idComerciante) {
        this.idComerciante = idComerciante;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDate fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }
}