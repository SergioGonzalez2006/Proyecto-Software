package com.kazuki_turismo.api_kazukiTurismo.dao;

import com.kazuki_turismo.api_kazukiTurismo.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void guardarReservaNativo(Reserva reserva) {
        String sql = "INSERT INTO reserva (fecha_inicio, fecha_final, huspedes, pago_total, estado_reserva, id_servicio, id_usuario) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                reserva.getFechaInicio(),
                reserva.getFechaFinal(),
                reserva.getHuspedes(),
                reserva.getPagoTotal(),
                reserva.getEstadoReserva(),
                reserva.getServicio().getIdServicio(),
                reserva.getUsuario().getIdUsuario()
        );
    }
}