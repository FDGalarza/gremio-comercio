package com.gremio;

// Importamos lo necesario de Spring Boot
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;



/**
 * Clase principal del backend.
 *
 * - @SpringBootApplication: configura el proyecto y levanta el servidor Tomcat embebido.
 * - @EnableCaching: habilita cache para consultas como municipios.
 * - @EnableMethodSecurity: permite el uso de @PreAuthorize y @PostAuthorize
 *   para proteger métodos y endpoints por roles.
 */
@SpringBootApplication
@EnableCaching
@EnableMethodSecurity  // Habilita la seguridad basada en anotaciones
public class GremioApiApplication {

    /**
     * El método main es el punto de entrada del proyecto.
     * Con esta línea:
     * SpringApplication.run(GremioApiApplication.class, args);
     * se levanta el servidor en el puerto 8080 por defecto.
     */
    public static void main(String[] args) {
        SpringApplication.run(GremioApiApplication.class, args);
    }
}
