package com.kazuki_turismo.frontend.model;

import lombok.Data;

@Data
public class ServicioDTO {
    private Integer idServicio;
    private String nombreHostal;
    private String tipoHostal;
    private Integer huespedesMax;
    private Double valor;
}
