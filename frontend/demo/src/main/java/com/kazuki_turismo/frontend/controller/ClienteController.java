package com.kazuki_turismo.frontend.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit; // noches de estadía

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

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ServicioClient servicioClient;

    @Autowired
    private ReservaClient reservaClient;

    // Bienvenida
    @GetMapping("/catalogo")
    public String verCatalogo(Model model) {
        model.addAttribute("servicios", servicioClient.listarTodos());
        return "cliente-catalogo";
    }

    // Parte reserva
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

    // Pasarela pago
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
        
        //Control de seguridad para asegurar que la fecha de inicio
        if (reserva.getFechaInicio() == null) {
            reserva.setFechaInicio(LocalDate.now());
        }

        LocalDate checkIn = reserva.getFechaInicio();
        LocalDate checkOut = LocalDate.parse(fechaSalidaStr);
        
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            checkOut = checkIn.plusDays(1);
        }
        
        reserva.setFechaFinal(checkOut);
        
        //Cálculo de las noches de estadía
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

    @PostMapping("/confirmar-pago")
    public String confirmarPago(@ModelAttribute("reserva") ReservaDTO reserva) {
        
        if (reserva.getUsuario() == null) {
            reserva.setUsuario(new UsuarioDTO());
        }
        
        reserva.getUsuario().setIdUsuario(null); 
        reserva.getUsuario().setRolUsuario("CLIENTE"); 
        
        if (reserva.getUsuario().getCorreo() == null || reserva.getUsuario().getCorreo().isEmpty()) {
            String nombreLimpio = reserva.getUsuario().getNombre().toLowerCase().replaceAll("\\s+", "");
            reserva.getUsuario().setCorreo(nombreLimpio + "@mail.com");
        }
        
        if (reserva.getUsuario().getContrasena() == null || reserva.getUsuario().getContrasena().isEmpty()) {
            reserva.getUsuario().setContrasena("1234");
        }

        if (reserva.getServicio() == null) {
            reserva.setServicio(new ServicioDTO());
        }

        // Enviamos la entidad limpia al Backend acoplado
        String respuestaBackend = reservaClient.crear(reserva);
        System.out.println("Servidor dice: " + respuestaBackend);
        
        return "cliente-exito";
    }

    // MÓDULOS DE AUTENTICACIÓN REAL

    // Formulario de login (Acceso por: http://localhost:8081/cliente/login)
    @GetMapping("/login")
    public String verLogin() {
        return "cliente-login"; 
    }

    // Procesar el Login consultando dinámicamente a la Base de Datos real
    @PostMapping("/login-procesar")
    public String procesarLogin(@RequestParam("correo") String correo, 
                                @RequestParam("contrasena") String contrasena, 
                                Model model) {
        try {
            // Buscamos si existe una reserva real con el usuario y contraseña correctos
            boolean usuarioValido = reservaClient.listarTodas().stream()
                .anyMatch(r -> r.getUsuario() != null 
                            && r.getUsuario().getCorreo() != null
                            && correo.equalsIgnoreCase(r.getUsuario().getCorreo().trim()) 
                            && r.getUsuario().getContrasena() != null 
                            && contrasena.equals(r.getUsuario().getContrasena().trim()));

            if (usuarioValido) {
                System.out.println("✅ Login exitoso en Base de Datos para: " + correo);
                return "redirect:/cliente/perfil?correo=" + correo; 
            } else {
                model.addAttribute("error", "El correo o contraseña de 4 dígitos ingresados no coinciden.");
                return "cliente-login"; 
            }
            
        } catch (Exception e) {
            model.addAttribute("error", "Error de comunicación con el servicio de autenticación.");
            return "cliente-login";
        }
    }

    // Mostrar el perfil
    @GetMapping("/perfil")
    public String verPerfil(@RequestParam(value = "correo", required = false) String correo, Model model) {
        if (correo == null || correo.isEmpty()) {
            return "redirect:/cliente/login";
        }

        try {
            // lista de reservas por el correo del cliente
            ReservaDTO reservaUsuario = reservaClient.listarTodas().stream()
                .filter(r -> r.getUsuario() != null && correo.equalsIgnoreCase(r.getUsuario().getCorreo().trim()))
                .findFirst().orElse(null);

            if (reservaUsuario != null) {
                // Pasamos las entidades reales de MySQL al HTML
                model.addAttribute("reserva", reservaUsuario);
                model.addAttribute("usuario", reservaUsuario.getUsuario());
                
                // MODELADO DE LA ENTIDAD "NOTIFICACION"
                model.addAttribute("idNotificacion", reservaUsuario.getIdReserva() * 7 + 12); 
                model.addAttribute("idReservaFk", reservaUsuario.getIdReserva()); 
                model.addAttribute("idUsuarioFk", reservaUsuario.getUsuario().getIdUsuario() != null ? reservaUsuario.getUsuario().getIdUsuario() : 1); 
                
                model.addAttribute("tipoNotificacion", "ALERTA DE CONFIRMACIÓN");
                model.addAttribute("mensajeEnviado", "Estimado(a) " + reservaUsuario.getUsuario().getNombre() + ", tu pago para el hospedaje '" + (reservaUsuario.getServicio() != null ? reservaUsuario.getServicio().getNombreHostal() : "Alojamiento") + "' ha sido validado correctamente.");
                model.addAttribute("fechaMensaje", reservaUsuario.getFechaInicio());
            } else {
                // Si el correo no existe en la base de datos.
                model.addAttribute("error", "No se encontraron registros activos para este usuario.");
                return "cliente-login";
            }

        } catch (Exception e) {
            model.addAttribute("error", "Error al sincronizar el buzón de notificaciones.");
            return "cliente-login";
        }

        return "cliente-perfil"; 
    }
}


    // Confirmar reserva usando los datos reales digitados en la pasarela
    /*
    @PostMapping("/confirmar-pago")
    public String confirmarPago(@ModelAttribute("reserva") ReservaDTO reserva) {
        
        // Control de seguridad 
        if (reserva.getUsuario() == null) {
            reserva.setUsuario(new UsuarioDTO());
        }
        
        reserva.getUsuario().setIdUsuario(null);
        
        // Asignamos el rol obligatorio para que la base de datos sepa que es un Cliente
        reserva.getUsuario().setRolUsuario("CLIENTE"); 
        
        if (reserva.getUsuario().getCorreo() == null || reserva.getUsuario().getCorreo().isEmpty()) {
            String nombreLimpio = reserva.getUsuario().getNombre().toLowerCase().replaceAll("\\s+", "");
            reserva.getUsuario().setCorreo(nombreLimpio + "@mail.com");
        }
        
        if (reserva.getUsuario().getContrasena() == null || reserva.getUsuario().getContrasena().isEmpty()) {
            reserva.getUsuario().setContrasena("1234");
        }

        // Enviamos toda la reserva con el usuario al Backend
        reservaClient.crear(reserva);
        
        return "cliente-exito";
    }
    */