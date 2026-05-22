package com.kazuki_turismo.api_kazukiTurismo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "disponibilidad")
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disponibilidad")
    private int idDisponibilidad;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @Column(name = "fechas_disponibles", nullable = false)
    private LocalDate fechasDisponibles;

    @Column(name = "cupos_disponibles", nullable = false)
    private int cuposDisponibles;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    // Getters y Setters
    public int getIdDisponibilidad() { return idDisponibilidad; }
    public void setIdDisponibilidad(int idDisponibilidad) { this.idDisponibilidad = idDisponibilidad; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }

    public LocalDate getFechasDisponibles() { return fechasDisponibles; }
    public void setFechasDisponibles(LocalDate fechasDisponibles) { this.fechasDisponibles = fechasDisponibles; }

    public int getCuposDisponibles() { return cuposDisponibles; }
    public void setCuposDisponibles(int cuposDisponibles) { this.cuposDisponibles = cuposDisponibles; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}