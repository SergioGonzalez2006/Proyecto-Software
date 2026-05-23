package com.kazuki_turismo.frontend.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kazuki_turismo.frontend.model.ServicioDTO;

@FeignClient(name = "servicio-client", url = "http://localhost:8080/api-kazukiTurismo/api/servicios")
public interface ServicioClient {

    @GetMapping
    List<ServicioDTO> listarTodos();

    @PostMapping
    ServicioDTO crear(@RequestBody ServicioDTO servicio);

    @PutMapping
    ServicioDTO actualizar(@RequestBody ServicioDTO servicio);

    @DeleteMapping("/{id}")
    void eliminar(@PathVariable("id") Integer id);
}