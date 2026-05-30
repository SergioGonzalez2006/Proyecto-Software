package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Reserva;
import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import com.kazuki_turismo.api_kazukiTurismo.repository.ReservaRepository;
import com.kazuki_turismo.api_kazukiTurismo.repository.ServicioRepository;
import com.kazuki_turismo.api_kazukiTurismo.repository.UsuarioRepository;
import com.kazuki_turismo.api_kazukiTurismo.dao.ReservaDAO;
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

    @Autowired
    private ReservaDAO reservaDAO;

    @GetMapping
    @Operation(summary = "Listar todas las reservas", description = "Obtiene el historial completo de reservas desde la base de datos")
    public List<Reserva> listar() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear una reserva", description = "Registra una nueva reserva vinculando correctamente las entidades existentes o creando usuarios en caliente.")
    public String crear(@RequestBody Reserva reserva) {

        // Validar que al menos venga la estructura del usuario y el servicio
        if (reserva.getUsuario() == null) {
            return "Error: Debe especificar los datos del usuario.";
        }
        if (reserva.getServicio() == null || reserva.getServicio().getIdServicio() == null) {
            return "Error: Debe especificar un ID de servicio válido.";
        }

        // 1. Validar la existencia del Servicio Turístico
        java.util.Optional<com.kazuki_turismo.api_kazukiTurismo.model.Servicio> servicioOpt =
                servicioRepository.findById(reserva.getServicio().getIdServicio());

        if (!servicioOpt.isPresent()) {
            return "Error: El servicio turístico con ID " + reserva.getServicio().getIdServicio() + " no existe.";
        }
        reserva.setServicio(servicioOpt.get());

        // 🎯 LÓGICA DE FUSIÓN E INTEGRACIÓN HÍBRIDA (Sana tu flujo en caliente)
        Usuario usuarioFinal = null;

        // Caso A: El Frontend envió un ID numérico (Flujo tradicional de Sergio)
        if (reserva.getUsuario().getIdUsuario() != null) {
            java.util.Optional<Usuario> usuarioOpt = usuarioRepository.findById(reserva.getUsuario().getIdUsuario());
            if (usuarioOpt.isPresent()) {
                usuarioFinal = usuarioOpt.get();
                System.out.println("🔄 Vinculando reserva a usuario existente con ID: " + usuarioFinal.getIdUsuario());
            }
        }
        
        // Caso B: El ID es nulo o no se encontró por ID (Flujo de Julissa: Daniel Quintero se registra en caliente)
        if (usuarioFinal == null) {
            System.out.println("✨ Detectado registro en caliente desde el formulario de pago. Buscando/Creando usuario...");
            
            // Salvavidas: Por si acaso ya existe un usuario registrado con ese mismo correo en la BD
            if (reserva.getUsuario().getCorreo() != null) {
                usuarioFinal = usuarioRepository.findAll().stream()
                    .filter(u -> u.getCorreo() != null && u.getCorreo().equalsIgnoreCase(reserva.getUsuario().getCorreo().trim()))
                    .findFirst().orElse(null);
            }

            // Si el correo es completamente nuevo, lo insertamos físicamente en MySQL en este instante
            if (usuarioFinal == null) {
                usuarioFinal = usuarioRepository.save(reserva.getUsuario());
                System.out.println("✅ Nuevo usuario insertado con éxito en MySQL. ID asignado: " + usuarioFinal.getIdUsuario());
            }
        }

        // Asociamos el usuario real (ya persistido y con ID) a la reserva
        reserva.setUsuario(usuarioFinal);

        // 2. Procesar el guardado final de la reserva con el DAO nativo de Sergio
        try {
            reservaDAO.guardarReservaNativo(reserva);
            return "Éxito: La reserva ha sido creada correctamente bajo el ID de usuario " + reserva.getUsuario().getIdUsuario() + ".";
        } catch (Exception e) {
            return "Error al procesar el guardado de la reserva con DAO: " + e.getMessage();
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