package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Servicio;
import com.kazuki_turismo.api_kazukiTurismo.repository.ServicioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "Controlador de Servicios", description = "Gestión del catálogo de servicios turísticos")
public class ServicioController {

    @Autowired
    private ServicioRepository repository;

    @GetMapping
    @Operation(summary = "Consultar catálogo de servicios", description = " servicios disponibles (tours, hoteles, transporte, etc.)")
    public List<Servicio> listar() {
        return repository.findAll();
    }
}