package com.kazuki_turismo.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kazuki_turismo.frontend.client.ServicioClient;
import com.kazuki_turismo.frontend.model.ServicioDTO;

@Controller
@RequestMapping("/servicios")
public class ServicioViewController {

    @Autowired
    private ServicioClient client;

    // SERVICIOS
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaServicios", client.listarTodos());
        return "servicios-lista"; //  html servicios-lista en templates
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(value = "id", required = false) Integer id, Model model) {
        ServicioDTO servicio = new ServicioDTO();
        if (id != null) {
            // Busqueda de el servicio por ID
            servicio = client.listarTodos().stream()
                    .filter(s -> s.getIdServicio().equals(id))
                    .findFirst().orElse(new ServicioDTO());
        }
        model.addAttribute("servicioDTO", servicio);
        return "servicios-formulario"; // Apunta a html servicios-formulario en templates
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("servicioDTO") ServicioDTO servicio) {
        if (servicio.getIdServicio() != null) {
            client.actualizar(servicio);
        } else {
            client.crear(servicio);
        }
        return "redirect:/servicios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        client.eliminar(id);
        return "redirect:/servicios";
    }
}
