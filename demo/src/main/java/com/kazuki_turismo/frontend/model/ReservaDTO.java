package com.kazuki_turismo.frontend.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservaDTO {
    private Integer idReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private Integer huspedes;
    private Double pagoTotal; 
    private String estadoReserva;  
    private ServicioDTO servicio;
    private UsuarioDTO usuario;
}