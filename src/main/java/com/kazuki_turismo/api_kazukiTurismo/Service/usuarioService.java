package com.kazuki_turismo.api_kazukiTurismo.Service;

import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import java.sql.SQLException;
import java.util.List;

public interface usuarioService {
    List<Usuario> listar() throws SQLException;
    String insertar(Usuario u);
    String actualizar(Usuario u);
    boolean eliminar(int id) throws SQLException;
}