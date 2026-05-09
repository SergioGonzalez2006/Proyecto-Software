package com.kazuki_turismo.api_kazukiTurismo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "reserva")
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReserva;

    private LocalDate fechaReserva;
    private Integer cantidadPersonas;
    private Double totalPagar;

    @ManyToOne
    @JoinColumn(name = "id_usuario") // Llave foránea a la tabla usuario
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_servicio") // Llave foránea a la tabla servicio
    private Servicio servicio;
}
