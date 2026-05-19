package com.kazuki_turismo.api_kazukiTurismo.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Reserva")
    private Integer idReserva;

    @Column(name = "Fecha_Inicio")
    private LocalDate fechaInicio;

    @Column(name = "Fecha_Final")
    private LocalDate fechaFinal;

    @Column(name = "Huspedes")
    private Integer huspedes;

    @Column(name = "PagoTotal")
    private Double pagoTotal;

    @Column(name = "EstadoReserva")
    private String estadoReserva;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Id_Usuario") //La llave foránea a la tabla usuario
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "Id_Servicio") //La llave foránea a la tabla servicio
    private Servicio servicio;
}