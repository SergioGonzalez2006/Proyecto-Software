package com.kazuki_turismo.api_kazukiTurismo.Controller;

import com.kazuki_turismo.api_kazukiTurismo.dao.UsuarioDAO;
import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @GetMapping
    public List<Usuario> obtenerTodos() {
        try {
            return usuarioDAO.listar();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
