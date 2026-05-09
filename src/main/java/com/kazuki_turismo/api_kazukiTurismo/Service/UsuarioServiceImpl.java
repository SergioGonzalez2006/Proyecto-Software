package com.kazuki_turismo.api_kazukiTurismo.Service;

import com.kazuki_turismo.api_kazukiTurismo.dao.UsuarioDAO;
import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;

@Service
public class UsuarioServiceImpl implements usuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public List<Usuario> listar() throws SQLException {
        return usuarioDAO.listar();
    }

    @Override
    public String insertar(Usuario u) {
        try {
            usuarioDAO.insertar(u);
            return "Usuario creado exitosamente";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(Usuario u) {
        try {
            usuarioDAO.actualizar(u);
            return "Usuario actualizado exitosamente";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        return usuarioDAO.eliminar(id);
    }
}