package com.kazuki_turismo.api_kazukiTurismo.dao;

import com.kazuki_turismo.api_kazukiTurismo.model.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PagoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registrarPagoNativo(Pago pago) {
        String sql = "INSERT INTO pago (id_reserva, pago_total, metodo_pago, estado_pago, fecha_pago) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                pago.getReserva().getIdReserva(),
                pago.getPagoTotal(),
                pago.getMetodoPago(),
                pago.getEstadoPago(),
                pago.getFechaPago()
        );
    }
}