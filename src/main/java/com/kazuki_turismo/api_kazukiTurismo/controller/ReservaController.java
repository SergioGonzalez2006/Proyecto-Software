package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Reserva;
import com.kazuki_turismo.api_kazukiTurismo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository repository;

    @GetMapping
    public List<Reserva> listar() {
        return repository.findAll();
    }

    @PostMapping
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