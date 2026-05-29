package com.kazuki_turismo.api_kazukiTurismo.dao;

import com.kazuki_turismo.api_kazukiTurismo.model.Notificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NotificacionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void guardarNotificacionNativo(Notificacion notificacion) {
        String sql = "INSERT INTO notificacion (id_reserva, id_usuario, tipo_notificacion, mensaje_enviado, fecha_mensaje) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                notificacion.getReserva().getIdReserva(),
                notificacion.getUsuario().getIdUsuario(),
                notificacion.getTipoNotificacion(),
                notificacion.getMensajeEnviado(),
                notificacion.getFechaMensaje()
        );
    }
}