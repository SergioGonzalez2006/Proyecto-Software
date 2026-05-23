package com.kazuki_turismo.frontend.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit; // noches de estadía
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kazuki_turismo.frontend.client.ReservaClient;
import com.kazuki_turismo.frontend.client.ServicioClient;
import com.kazuki_turismo.frontend.model.ReservaDTO;
import com.kazuki_turismo.frontend.model.ServicioDTO;
import com.kazuki_turismo.frontend.model.UsuarioDTO;

import jakarta.servlet.http.HttpSession; // Accesos dinámicos

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ServicioClient servicioClient;

    @Autowired
    private ReservaClient reservaClient;

    @Autowired
    private HttpSession session; // Sesión para auditar quién está operando


    // Mostrar el formulario de Login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "cliente-login";
    }

    // Procesar autenticación cruzando datos
    @PostMapping("/login")
    public String procesarLogin(@RequestParam("correo") String correo, 
                                @RequestParam("contrasena") String contrasena, 
                                Model model) {
        
        // Acepta la clave 1234
        if (contrasena.equals("1234")) {

            String nombreFormateado = correo.split("@")[0].toUpperCase();
            
            session.setAttribute("idUsuarioLogueado", 1); // ID asignado por defecto para pruebas relacionales
            session.setAttribute("usuarioLogueado", correo);
            session.setAttribute("nombreUsuario", nombreFormateado);
            
            return "redirect:/cliente/catalogo"; // Navega directamente al catálogo autenticado
        } else {
            model.addAttribute("error", "Contraseña incorrecta. Intenta con la clave corporativa 1234");
            return "cliente-login";
        }
    }

    // Destrucción segura de la sesión HTTP
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/cliente/login";
    }


    // Parte bienvenida: Catálogo de Alojamientos
    @GetMapping("/catalogo")
    public String verCatalogo(Model model) {
        model.addAttribute("servicios", servicioClient.listarTodos());
        return "cliente-catalogo";
    }

    // Parte reserva: Inicializar orden
    @GetMapping("/reservar/{idServicio}")
    public String iniciarReserva(@PathVariable("idServicio") Integer idServicio, Model model) {
        ServicioDTO servicio = servicioClient.listarTodos().stream()
                .filter(s -> s.getIdServicio().equals(idServicio))
                .findFirst().orElse(null);

        ReservaDTO nuevaReserva = new ReservaDTO();
        nuevaReserva.setServicio(servicio);
        nuevaReserva.setFechaInicio(LocalDate.now());
        model.addAttribute("reserva", nuevaReserva);
        return "cliente-formulario-reserva";
    }

    // Parte pasarela pago: Calcular tarifas por noches
    @PostMapping("/checkout")
    public String irAPago(
            @ModelAttribute("reserva") ReservaDTO reserva, 
            @RequestParam(value = "fechaSalida", required = false) String fechaSalidaStr,
            @RequestParam("idServicioOculto") Integer idServicio, 
            Model model) {
            
        if (fechaSalidaStr == null || fechaSalidaStr.isEmpty()) {
            fechaSalidaStr = LocalDate.now().plusDays(1).toString();
        }
        
        if (reserva == null) {
            reserva = new ReservaDTO();
        }
        
        if (reserva.getServicio() == null) {
            reserva.setServicio(new ServicioDTO());
        }
        reserva.getServicio().setIdServicio(idServicio);

        double valorPorNoche = servicioClient.listarTodos().stream()
                .filter(s -> s.getIdServicio().equals(idServicio))
                .findFirst().get().getValor();
        
        if (reserva.getFechaInicio() == null) {
            reserva.setFechaInicio(LocalDate.now());
        }

        LocalDate checkIn = reserva.getFechaInicio();
        LocalDate checkOut = LocalDate.parse(fechaSalidaStr);
        
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            checkOut = checkIn.plusDays(1);
        }
        
        reserva.setFechaFinal(checkOut);
        
        long noches = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFinal());
        if (noches <= 0) {
            noches = 1; 
        }
        
        reserva.setPagoTotal(valorPorNoche * noches);
        reserva.setEstadoReserva("Confirmado con Tarjeta"); 
        
        model.addAttribute("reserva", reserva);
        model.addAttribute("nochesEstadia", noches); 
        
        return "cliente-pasarela-pago";
    }

    // Parte confirmar reserva: Enlace relacional con el ID de Sesión de la base de datos
    @PostMapping("/confirmar-pago")
    public String confirmarPago(@ModelAttribute("reserva") ReservaDTO reserva) {
        
        if (reserva.getUsuario() == null) {
            reserva.setUsuario(new UsuarioDTO());
        }
        
        // 🎯 SIMULACIÓN DE SESIÓN AUTOMÁTICA: 
        // Inyectamos un ID real (usualmente el 1 es el primer registro de tu base de datos)
        reserva.getUsuario().setIdUsuario(1); 
        reserva.getUsuario().setNombre("Julissa Mendoza");
        reserva.getUsuario().setCorreo("julissa@mail.com");
        reserva.getUsuario().setContrasena("1234"); 
        reserva.getUsuario().setRolUsuario("CLIENTE"); 

        // Enviamos el objeto completamente estructurado al Backend vía Feign Client
        reservaClient.crear(reserva);
        
        return "cliente-exito";
    }

    // PORTAL AUTÓNOMO: AUTOGESTIÓN DE CLIENTES

    // Cargar el perfil filtrando estrictamente los registros que le corresponden al ID logueado
    @GetMapping("/perfil")
    public String verPerfilCliente(Model model) {
        // En lugar de leer la sesión, simulamos que el usuario actual es el ID 1
        List<ReservaDTO> misReservas = reservaClient.listarTodas().stream()
                .filter(r -> r.getUsuario() != null && r.getUsuario().getIdUsuario().equals(1))
                .toList();

        model.addAttribute("misReservas", misReservas);
        model.addAttribute("emailCliente", "julissa@mail.com");
        model.addAttribute("nombreCliente", "JULISSA CASTRILLON");
        return "cliente-perfil";
    }

    // Cancelar/Eliminar un itinerario de forma autónoma
    @GetMapping("/reservas/cancelar/{id}")
    public String clienteCancelarReserva(@PathVariable("id") int id) {
        reservaClient.eliminar(id);
        return "redirect:/cliente/perfil";
    }

    // Abrir la interfaz para reprogramar las fechas
    @GetMapping("/reservas/editar/{id}")
    public String clienteEditarReservaForm(@PathVariable("id") int id, Model model) {
        ReservaDTO reservaACambiar = reservaClient.listarTodas().stream()
                .filter(r -> r.getIdReserva() == id)
                .findFirst().orElse(null);
                
        model.addAttribute("reserva", reservaACambiar);
        return "cliente-reprogramar";
    }

    // Procesar el formulario de actualización e invocar el PUT del backend
    @PostMapping("/reservas/actualizar")
    public String clienteActualizarReserva(@ModelAttribute("reserva") ReservaDTO reserva) {
        reservaClient.actualizar(reserva.getIdReserva(), reserva);
        return "redirect:/cliente/perfil";
    }
}