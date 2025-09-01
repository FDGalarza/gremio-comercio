package com.gremio.repository;

import com.gremio.dto.ComercianteReporteDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ComercianteReporteRepository {

    private final JdbcTemplate jdbcTemplate;

    public ComercianteReporteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ComercianteReporteDTO> obtenerComerciantesActivos() {
        String sql = "{ ? = call pkg_comerciantes.obtener_comerciantes_activos() }";

        return jdbcTemplate.execute((Connection conn) -> {
            CallableStatement cs = conn.prepareCall(sql);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            return cs;
        }, (CallableStatement cs) -> {
            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(1);
            List<ComercianteReporteDTO> lista = new ArrayList<>();
            while (rs.next()) {
                ComercianteReporteDTO dto = new ComercianteReporteDTO();
                dto.setNombreRazonSocial(rs.getString("nombre_razon_social"));
                dto.setMunicipio(rs.getString("municipio"));
                dto.setTelefono(rs.getString("telefono"));
                dto.setCorreoElectronico(rs.getString("correo_electronico"));
                if (rs.getDate("fecha_registro") != null) {
                    dto.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                }
                dto.setEstado(rs.getString("estado"));
                dto.setCantidadEstablecimientos(rs.getInt("cantidad_establecimientos"));
                dto.setTotalIngresos(rs.getDouble("total_ingresos"));
                dto.setCantidadEmpleados(rs.getInt("cantidad_empleados"));
                lista.add(dto);
            }
            rs.close();
            return lista;
        });
    }
}