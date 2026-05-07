package com.kazuki_turismo.api_kazukiTurismo.controller;

import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import com.kazuki_turismo.api_kazukiTurismo.Service.usuarioService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private usuarioService service; 

    @GetMapping
    public List<Usuario> listar() throws SQLException {
        return service.listar();
    }

    @PostMapping
    public String insertar(@RequestBody Usuario usuario) {

        return service.insertar(usuario);
    }

    @PutMapping
    public String actualizar(@RequestBody Usuario usuario) {
        return service.actualizar(usuario);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) throws SQLException {
        boolean eliminado = service.eliminar(id);
        if (eliminado) {
            return "Éxito: El usuario con ID " + id + " ha sido eliminado.";
        } else {
            return "Aviso: No se encontró ningún usuario con el ID " + id + ". Nada fue borrado.";
        }
    }
}