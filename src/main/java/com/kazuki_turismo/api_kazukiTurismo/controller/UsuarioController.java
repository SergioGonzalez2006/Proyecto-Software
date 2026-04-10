package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final com.kazuki_turismo.api_kazukiTurismo.Service.usuarioService usuarioService = new com.kazuki_turismo.api_kazukiTurismo.Service.usuarioService();

    @GetMapping
    public List<Usuario> listar() throws SQLException {
        return usuarioService.obtenerTodos();
    }

    @PostMapping
    public String guardar(@RequestBody Usuario u) throws SQLException {
        usuarioService.crear(u);
        return "Usuario creado exitosamente";
    }

    @PutMapping
    public String actualizar(@RequestBody Usuario u) throws SQLException {
        usuarioService.editar(u);
        return "Usuario actualizado";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) throws SQLException {
        usuarioService.borrar(id);
        return "Usuario eliminado con ID: " + id;
    }
}