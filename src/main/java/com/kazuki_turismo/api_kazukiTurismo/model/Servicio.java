package com.kazuki_turismo.api_kazukiTurismo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "servicio")
@Data
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idServicio;

    private String nombreHostal;
    private String tipoHostal;
    private Integer huespedesMax;
    private Double valor;
}
