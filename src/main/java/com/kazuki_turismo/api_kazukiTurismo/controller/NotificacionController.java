package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Notificacion;
import com.kazuki_turismo.api_kazukiTurismo.repository.NotificacionRepository;
import com.kazuki_turismo.api_kazukiTurismo.repository.ReservaRepository;
import com.kazuki_turismo.api_kazukiTurismo.dao.UsuarioDAO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Controlador de Notificaciones", description = "Gestión del sistema de alertas para los usuarios y reservas")
public class NotificacionController {

    @Autowired
    private NotificacionRepository repository;
    @Autowired
    private ReservaRepository reservaRepository;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @GetMapping
    @Operation(summary = "Listar alertas enviadas", description = "Retorna el historial completo de mensajes y tipos de notificaciones emitidas.")
    public List<Notificacion> listar() {
        return repository.findAll();
    }
    @PostMapping
    @Operation(summary = "Crear nueva notificación", description = "Genera una alerta validando que existan el usuario y la reserva")
    public String guardar(@RequestBody Notificacion notificacion) {
        if (notificacion.getMensajeEnviado() == null || notificacion.getMensajeEnviado().trim().isEmpty()) {
            return "Error: El cuerpo del mensaje de la notificación no puede estar vacío.";
        }

        if (notificacion.getReserva() == null || !reservaRepository.existsById(notificacion.getReserva().getIdReserva())) {
            return "Error: La reserva asociada no existe.";
        }

        try {
            if (notificacion.getUsuario() == null || !usuarioDAO.existeId(notificacion.getUsuario().getIdUsuario())) {
                return "Error: El usuario asociado no existe en el sistema.";
            }
        } catch (java.sql.SQLException e) {
            return "Error en la base de datos al validar el usuario: " + e.getMessage();
        }

        repository.save(notificacion);
        return "Éxito: Notificación registrada y enviada en el sistema.";
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar notificación por ID", description = "Modifica el contenido o estado de un mensaje de alerta registrado.")
    public String actualizar(@PathVariable int id, @RequestBody Notificacion notificacion) {

        if (!repository.existsById(id)) {
            return "Aviso: No se encontró ninguna notificación con el ID " + id + " para actualizar.";
        }

        if (notificacion.getReserva() == null || !reservaRepository.existsById(notificacion.getReserva().getIdReserva())) {
            return "Error: La reserva asociada no existe.";
        }

        try {
            if (notificacion.getUsuario() == null || !usuarioDAO.existeId(notificacion.getUsuario().getIdUsuario())) {
                return "Error: El usuario asociado no existe.";
            }
        } catch (java.sql.SQLException e) {
            return "Error al validar el usuario: " + e.getMessage();
        }
        notificacion.setIdNotificacion(id);
        repository.save(notificacion);
        return "Éxito: La notificación con ID " + id + " ha sido actualizada.";
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar notificación por ID", description = "Borra una alerta del historial.")
    public String eliminar(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Éxito: La notificación con ID " + id + " ha sido eliminada.";
        } else {
            return "Aviso: No se encontró ninguna notificación con el ID " + id + ". Nada fue borrado.";
        }
    }
}