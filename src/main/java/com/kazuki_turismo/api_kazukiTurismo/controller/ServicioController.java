package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Servicio;
import com.kazuki_turismo.api_kazukiTurismo.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository repository;

    @GetMapping
    public List<Servicio> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Servicio crear(@RequestBody Servicio servicio) {
        return repository.save(servicio);
    }

    @PutMapping
    public Servicio actualizar(@RequestBody Servicio servicio) {
        return repository.save(servicio);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
