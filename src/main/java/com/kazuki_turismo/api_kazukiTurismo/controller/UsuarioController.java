package com.kazuki_turismo.api_kazukiTurismo.controller;

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
    public List<Usuario> listar() throws SQLException {
        return usuarioDAO.listar();
    }

    @PostMapping
    public String crear(@RequestBody Usuario u) throws SQLException {
        usuarioDAO.insertar(u);
        return "Usuario creado exitosamente con SQL puro";
    }

    @PutMapping
    public String editar(@RequestBody Usuario u) throws SQLException {
        usuarioDAO.actualizar(u);
        return "Usuario actualizado correctamente";
    }

    @DeleteMapping("/{id}")
    public String borrar(@PathVariable int id) throws SQLException {
        usuarioDAO.eliminar(id);
        return "Usuario eliminado con ID: " + id;
    }
}