package com.gremio.model;

import jakarta.persistence.*;

// Marca esta clase como una entidad JPA
@Entity
@Table(name = "usuario") // Nombre de la tabla en la BD
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autogenerado
    private Long id;

    private String nombre; // Nombre del usuario

    @Column(unique = true) // El correo debe ser único
    private String correoElectronico;

    private String contrasena; // Contraseña en texto plano (luego se encripta)

    @Enumerated(EnumType.STRING) // Enum como texto
    private Rol rol; // Rol del usuario (ADMINISTRADOR o AUXILIAR)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    
}
