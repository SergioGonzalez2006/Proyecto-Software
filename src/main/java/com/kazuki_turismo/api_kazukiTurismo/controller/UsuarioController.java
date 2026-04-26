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
    public String insertar(@RequestBody Usuario usuario) {
        try {
            if (usuario.getIdUsuario() > 0 && usuarioDAO.existeId(usuario.getIdUsuario())) {
                return "No se pudo crear: Ya existe un usuario con el ID " + usuario.getIdUsuario();
            }

            usuarioDAO.insertar(usuario);
            return "Usuario creado exitosamente";
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                return "No se pudo crear: El correo electrónico ya está registrado.";
            }
            return "Error al crear usuario: " + e.getMessage();
        }
    }

    @PutMapping
    public String actualizar(@RequestBody Usuario usuario) {
        try {
            usuarioDAO.actualizar(usuario);
            return "Usuario actualizado exitosamente";
        } catch (SQLException e) {
            return "Error al actualizar usuario: " + e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        try {
            boolean eliminado = usuarioDAO.eliminar(id);
            if (eliminado) {
                return "Éxito: El usuario con ID " + id + " ha sido eliminado.";
            } else {
                return "Aviso: No se encontró ningún usuario con el ID " + id + ". Nada fue borrado.";
            }
        } catch (SQLException e) {
            return "Error técnico: " + e.getMessage();
        }
    }
}