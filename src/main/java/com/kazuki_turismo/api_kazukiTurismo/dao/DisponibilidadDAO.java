package com.kazuki_turismo.api_kazukiTurismo.dao;

import com.kazuki_turismo.api_kazukiTurismo.model.Disponibilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DisponibilidadDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void guardarDisponibilidadNativo(Disponibilidad disponibilidad) {
        String sql = "INSERT INTO disponibilidad (id_servicio, fechas_disponibles, cupos_disponibles, estado) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                disponibilidad.getServicio().getIdServicio(),
                disponibilidad.getFechasDisponibles(),
                disponibilidad.getCuposDisponibles(),
                disponibilidad.getEstado()
        );
    }

    public void actualizarCuposNativo(Integer idDisponibilidad, int nuevosCupos) {
        String sql = "UPDATE disponibilidad SET cupos_disponibles = ? WHERE id_disponibilidad = ?";
        jdbcTemplate.update(sql, nuevosCupos, idDisponibilidad);
    }
}