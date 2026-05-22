package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Pago;
import com.kazuki_turismo.api_kazukiTurismo.repository.PagoRepository;
import com.kazuki_turismo.api_kazukiTurismo.repository.ReservaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Controlador de Pagos", description = "Gestión completa del flujo contable")
public class PagoController {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping
    @Operation(summary = "Listar todos los pagos", description = "Muestra el historial de todas las transacciones monetarias registradas.")
    public List<Pago> listar() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Registrar un pago", description = "Crea una nueva transacción validando el monto y la existencia de la reserva vinculada.")
    public String guardar(@RequestBody Pago pago) {
        if (pago.getReserva() == null || pago.getReserva().getIdReserva() == 0) {
            return "Error: Debe especificar una reserva válida para procesar el pago.";
        }

        if (!reservaRepository.existsById(pago.getReserva().getIdReserva())) {
            return "Error: La reserva con ID " + pago.getReserva().getIdReserva() + " no existe. Pago cancelado.";
        }

        if (pago.getPagoTotal() == null || pago.getPagoTotal().compareTo(BigDecimal.ZERO) <= 0) {
            return "Error: El valor total del pago debe ser una cantidad mayor a cero.";
        }

        repository.save(pago);
        return "Éxito: El pago ha sido registrado correctamente.";
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estado de pago", description = "Modifica los atributos del pago (como pasar de 'PENDIENTE' a 'APROBADO') controlando errores.")
    public String actualizar(@PathVariable int id, @RequestBody Pago pago) {
        if (!repository.existsById(id)) {
            return "Aviso: No se encontró ningún registro de pago con el ID " + id + " para modificar.";
        }

        if (pago.getReserva() == null || !reservaRepository.existsById(pago.getReserva().getIdReserva())) {
            return "Error: La reserva vinculada a este pago no existe.";
        }

        pago.setIdPago(id);
        repository.save(pago);
        return "Éxito: El registro de pago con ID " + id + " ha sido actualizado.";
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro de pago", description = "Borra una transacción financiera del sistema controlando el error 500.")
    public String eliminar(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Éxito: El registro de pago con ID " + id + " ha sido eliminado.";
        } else {
            return "Aviso: No se encontró ningún pago con el ID " + id + ". Nada fue borrado.";
        }
    }
}