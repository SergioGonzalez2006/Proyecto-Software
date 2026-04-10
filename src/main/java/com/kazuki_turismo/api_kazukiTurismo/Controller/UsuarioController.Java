package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.dao.UsuarioDAO;
import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "CRUD de usuarios con SQL manual y Driver JDBC")
public class UsuarioController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @GetMapping
    @Operation(summary = "Listar todos los usuarios")
    public List<Usuario> obtenerTodos() {
        try { return usuarioDAO.listar(); } 
        catch (SQLException e) { e.printStackTrace(); return null; }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario")
    public String crear(@RequestBody Usuario usuario) {
        try {
            usuarioDAO.insertar(usuario);
            return "Usuario creado exitosamente";
        } catch (SQLException e) { return "Error: " + e.getMessage(); }
    }
}