package com.gremio.service;

import com.gremio.model.Municipio;
import com.gremio.repository.MunicipioRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipioService {

    private final MunicipioRepository municipioRepository;

    public MunicipioService(MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
    }

    // Método para obtener la lista de municipios con cache en memoria
    @Cacheable("municipios")
    public List<Municipio> obtenerMunicipios() {
        return municipioRepository.findAll();
    }

    // -------------------------------
    // Nuevo método para limpiar cache
    // -------------------------------
    /**
     * Este método elimina el cache en memoria asociado a "municipios".
     * Sirve cuando actualizamos o cargamos nuevos municipios y queremos
     * que las siguientes consultas devuelvan datos frescos.
     */
    @CacheEvict(value = "municipios", allEntries = true)
    public void limpiarCache() {
        // No hace falta lógica interna, la anotación se encarga
    }
}