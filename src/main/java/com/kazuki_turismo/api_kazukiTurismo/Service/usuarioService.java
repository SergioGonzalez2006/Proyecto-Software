package com.kazuki_turismo.api_kazukiTurismo.Service;
import com.kazuki_turismo.api_kazukiTurismo.dao.UsuarioDAO;
import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import java.sql.SQLException;
import java.util.List;

public class usuarioService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public List<Usuario> obtenerTodos() throws SQLException { return usuarioDAO.listar(); }
    public void crear(Usuario u) throws SQLException { usuarioDAO.insertar(u); }
    public void editar(Usuario u) throws SQLException { usuarioDAO.actualizar(u); }
    public void borrar(int id) throws SQLException { usuarioDAO.eliminar(id); }
}