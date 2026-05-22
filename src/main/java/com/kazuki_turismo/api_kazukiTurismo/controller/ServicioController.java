package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Servicio;
import com.kazuki_turismo.api_kazukiTurismo.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "servicio-controller", description = "Operaciones del catálogo de servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository repository;

    @GetMapping
    @Operation(summary = "Listar todos los servicios", description = "Retorna una lista con todos los registros de servicios de la base de datos.")
    public List<Servicio> listar() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear un servicio", description = "Registra un nuevo servicio en el catálogo de la agencia.")
    public Servicio crear(@RequestBody Servicio servicio) {
        return repository.save(servicio);
    }

    @PutMapping
    @Operation(summary = "Actualizar un servicio", description = "Modifica los datos de un servicio existente en el sistema.")
    public Servicio actualizar(@RequestBody Servicio servicio) {
        return repository.save(servicio);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un servicio por ID", description = "Borra físicamente del catálogo el servicio especificado por su identificador único.")
    public void eliminar(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}