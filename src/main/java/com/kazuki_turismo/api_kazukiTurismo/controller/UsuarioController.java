package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import com.kazuki_turismo.api_kazukiTurismo.Service.usuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Controlador de Usuarios", description = "Gestion de Usuarios")
public class UsuarioController {

    @Autowired
    private usuarioService service;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Retorna una lista con todos los usuarios registrados en la base de datos")
    public List<Usuario> listar() throws SQLException {
        return service.listar();
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    public String insertar(@RequestBody Usuario usuario) {
        return service.insertar(usuario);
    }

    @PutMapping
    @Operation(summary = "Actualizar un usuario existente", description = "Modifica los datos de un usuario segun su ID proporcionada")
    public String actualizar(@RequestBody Usuario usuario) {
        return service.actualizar(usuario);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario por ID", description = "Elimina el usuario con un ID en especifico")
    public String eliminar(@PathVariable int id) throws SQLException {
        boolean eliminado = service.eliminar(id);
        if (eliminado) {
            return "Éxito: El usuario con ID " + id + " ha sido eliminado.";
        } else {
            return "Aviso: No se encontró ningún usuario con el ID " + id + ". Nada fue borrado.";
        }
    }
}