package com.kazuki_turismo.api_kazukiTurismo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notificacion")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private int idNotificacion;

    @ManyToOne
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "tipo_notificacion", nullable = false, length = 100)
    private String tipoNotificacion;

    @Column(name = "mensaje_enviado", nullable = false, columnDefinition = "TEXT")
    private String mensajeEnviado;

    @Column(name = "fecha_mensaje", nullable = false)
    private LocalDate fechaMensaje;

    // Getters y Setters
    public int getIdNotificacion() { return idNotificacion; }
    public void setIdNotificacion(int idNotificacion) { this.idNotificacion = idNotificacion; }

    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getTipoNotificacion() { return tipoNotificacion; }
    public void setTipoNotificacion(String tipoNotificacion) { this.tipoNotificacion = tipoNotificacion; }

    public String getMensajeEnviado() { return mensajeEnviado; }
    public void setMensajeEnviado(String mensajeEnviado) { this.mensajeEnviado = mensajeEnviado; }

    public LocalDate getFechaMensaje() { return fechaMensaje; }
    public void setFechaMensaje(LocalDate fechaMensaje) { this.fechaMensaje = fechaMensaje; }
}