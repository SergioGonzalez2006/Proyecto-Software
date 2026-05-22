package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Disponibilidad;
import com.kazuki_turismo.api_kazukiTurismo.repository.DisponibilidadRepository;
import com.kazuki_turismo.api_kazukiTurismo.repository.ServicioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidades")
@Tag(name = "Controlador de Disponibilidad", description = "Gestión integral de fechas y cupos para servicios")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadRepository repository;

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping
    @Operation(summary = "Consultar todas las disponibilidades", description = "Retorna el listado completo de fechas y cupos turísticos registrados.")
    public List<Disponibilidad> listar() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear disponibilidad", description = "Registra un nuevo rango de fechas y cupos para un servicio, validando que este exista.")
    public String guardar(@RequestBody Disponibilidad disponibilidad) {
        if (disponibilidad.getServicio() == null || disponibilidad.getServicio().getIdServicio() == 0) {
            return "Error: Debe especificar un servicio válido.";
        }

        if (!servicioRepository.existsById(disponibilidad.getServicio().getIdServicio())) {
            return "Error: El servicio con ID " + disponibilidad.getServicio().getIdServicio() + " no existe en el sistema.";
        }

        if (disponibilidad.getCuposDisponibles() < 0) {
            return "Error: Los cupos disponibles no pueden ser una cantidad negativa.";
        }

        repository.save(disponibilidad);
        return "Éxito: La disponibilidad se ha guardado correctamente.";
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar disponibilidad por ID", description = "Modifica los cupos o estados de un registro existente validando su ID.")
    public String actualizar(@PathVariable int id, @RequestBody Disponibilidad disponibilidad) {
        if (!repository.existsById(id)) {
            return "Aviso: No se encontró la disponibilidad con ID " + id + " para modificar.";
        }

        if (disponibilidad.getServicio() == null || !servicioRepository.existsById(disponibilidad.getServicio().getIdServicio())) {
            return "Error: El servicio asociado no existe.";
        }

        disponibilidad.setIdDisponibilidad(id);
        repository.save(disponibilidad);
        return "Éxito: La disponibilidad con ID " + id + " ha sido actualizada.";
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar disponibilidad por ID", description = "Remueve permanentemente un registro de cupos evitando errores de consistencia.")
    public String eliminar(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Éxito: La disponibilidad con ID " + id + " ha sido eliminada.";
        } else {
            return "Aviso: No se encontró ninguna disponibilidad con el ID " + id + ". Nada fue borrado.";
        }
    }
}