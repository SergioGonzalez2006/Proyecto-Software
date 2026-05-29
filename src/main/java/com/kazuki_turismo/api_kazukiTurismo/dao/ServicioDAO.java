package com.kazuki_turismo.api_kazukiTurismo.dao;

import com.kazuki_turismo.api_kazukiTurismo.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ServicioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private final RowMapper<Servicio> servicioRowMapper = (rs, rowNum) -> {
        Servicio servicio = new Servicio();
        servicio.setIdServicio(rs.getInt("id_servicio"));
        servicio.setNombreHostal(rs.getString("nombre_hostal"));
        servicio.setTipoHostal(rs.getString("tipo_hostal"));
        servicio.setHuespedesMax(rs.getInt("huespedes_max"));
        servicio.setValor(rs.getDouble("valor"));
        return servicio;
    };
    public List<Servicio> obtenerTodosLosServiciosNativo() {
        String sql = "SELECT id_servicio, nombre_hostal, tipo_hostal, huespedes_max, valor FROM servicio";
        return jdbcTemplate.query(sql, servicioRowMapper);
    }
}