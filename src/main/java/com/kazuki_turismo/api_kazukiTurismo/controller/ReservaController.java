package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Reserva;
import com.kazuki_turismo.api_kazukiTurismo.repository.ReservaRepository;
import com.kazuki_turismo.api_kazukiTurismo.repository.ServicioRepository;
import com.kazuki_turismo.api_kazukiTurismo.repository.UsuarioRepository;
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

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Listar todas las reservas", description = "Obtiene el historial completo de reservas desde la base de datos")
    public List<Reserva> listar() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear una reserva", description = "Registra una nueva reserva vinculando correctamente las entidades existentes en la base de datos.")
    public String crear(@RequestBody Reserva reserva) {

        if (reserva.getUsuario() == null || reserva.getUsuario().getIdUsuario() == null) {
            return "Error: Debe especificar un ID de usuario válido.";
        }
        if (reserva.getServicio() == null || reserva.getServicio().getIdServicio() == null) {
            return "Error: Debe especificar un ID de servicio válido.";
        }

        java.util.Optional<com.kazuki_turismo.api_kazukiTurismo.model.Servicio> servicioOpt =
                servicioRepository.findById(reserva.getServicio().getIdServicio());

        if (!servicioOpt.isPresent()) {
            return "Error: El servicio turístico con ID " + reserva.getServicio().getIdServicio() + " no existe.";
        }

        java.util.Optional<com.kazuki_turismo.api_kazukiTurismo.model.Usuario> usuarioOpt =
                usuarioRepository.findById(reserva.getUsuario().getIdUsuario());

        if (!usuarioOpt.isPresent()) {
            return "Error: El usuario con ID " + reserva.getUsuario().getIdUsuario() + " no está registrado en el sistema.";
        }

        reserva.setServicio(servicioOpt.get());
        reserva.setUsuario(usuarioOpt.get());


        try {
            repository.save(reserva);
            return "Éxito: La reserva ha sido creada correctamente bajo el ID de usuario " + reserva.getUsuario().getIdUsuario() + ".";
        } catch (Exception e) {
            return "Error al procesar el guardado de la reserva: " + e.getMessage();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Reserva por ID", description = "Modifica los datos de una reserva existente asegurando que el ID y sus relaciones sean válidos.")
    public Reserva actualizar(@PathVariable Integer id, @RequestBody Reserva reserva) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró ninguna reserva con el ID: " + id);
        }

        if (reserva.getServicio() != null && !servicioRepository.existsById(reserva.getServicio().getIdServicio())) {
            throw new IllegalArgumentException("El servicio especificado no existe.");
        }

        if (reserva.getUsuario() != null && !usuarioRepository.existsById(reserva.getUsuario().getIdUsuario())) {
            throw new IllegalArgumentException("El usuario especificado no existe.");
        }

        reserva.setIdReserva(id);
        return repository.save(reserva);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Reserva", description = "Elimina tu reserva actual")
    public void eliminar(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}