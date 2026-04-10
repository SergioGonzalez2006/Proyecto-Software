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

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario existente")
    public String actualizar(@PathVariable int id, @RequestBody Usuario usuario) {
        try {
            usuario.setIdUsuario(id);
            usuarioDAO.actualizar(usuario);
            return "Usuario actualizado exitosamente";
        } catch (SQLException e) { return "Error: " + e.getMessage(); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario")
    public String eliminar(@PathVariable int id) {
        try {
            usuarioDAO.eliminar(id);
            return "Usuario eliminado exitosamente";
        } catch (SQLException e) { return "Error: " + e.getMessage(); }
    }
}