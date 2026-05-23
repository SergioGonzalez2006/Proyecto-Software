package com.kazuki_turismo.frontend.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kazuki_turismo.frontend.model.ReservaDTO;

@FeignClient(name = "reserva-client", url = "http://localhost:8080/api-kazukiTurismo/api/reservas")
public interface ReservaClient {

@PostMapping
    ReservaDTO crear(@RequestBody ReservaDTO reserva);

    @GetMapping
    List<ReservaDTO> listarTodas();

    // Mapeo para conectar con el @PutMapping("/{id}") del Backend
    @PutMapping("/{id}")
    ReservaDTO actualizar(@PathVariable("id") int id, @RequestBody ReservaDTO reserva);

    // Mapeo para conectar con el @DeleteMapping("/{id}") del Backend
    @DeleteMapping("/{id}")
    void eliminar(@PathVariable("id") int id);
}

