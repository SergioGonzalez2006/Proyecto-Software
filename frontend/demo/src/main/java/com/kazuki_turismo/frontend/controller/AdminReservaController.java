package com.kazuki_turismo.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kazuki_turismo.frontend.client.ReservaClient;

@Controller
@RequestMapping("/admin/reservas")
public class AdminReservaController {

    @Autowired
    private ReservaClient reservaClient;

    @GetMapping
    public String verPanelReservas(Model model) {
        model.addAttribute("listaReservas", reservaClient.listarTodas());
        return "admin-reservas-lista";
    }
}
