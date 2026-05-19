package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Reserva;
import com.kazuki_turismo.api_kazukiTurismo.repository.ReservaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Controlador de Reservas", description = "Gestión de reservaciones")
public class ReservaController {

    @Autowired
    private ReservaRepository repository;

    @GetMapping
    @Operation(summary = "Listar todas las reservas", description = "Obtiene el historial completo de reservas desde la base de datos")
    public List<Reserva> listar() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear una reserva", description = "Registra una nueva reserva")
    public Reserva crear(@RequestBody Reserva reserva) {
        return repository.save(reserva);
    }

    @PutMapping
    public Reserva actualizar(@RequestBody Reserva reserva) {
        return repository.save(reserva);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}