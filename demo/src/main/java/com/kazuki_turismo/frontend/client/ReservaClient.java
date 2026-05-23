package com.kazuki_turismo.frontend.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kazuki_turismo.frontend.model.ReservaDTO;

@FeignClient(name = "reserva-client", url = "http://localhost:8080/api-kazukiTurismo/api/reservas")
public interface ReservaClient {

    @PostMapping
    ReservaDTO crear(@RequestBody ReservaDTO reserva);

    @GetMapping
    List<ReservaDTO> listarTodas();
}