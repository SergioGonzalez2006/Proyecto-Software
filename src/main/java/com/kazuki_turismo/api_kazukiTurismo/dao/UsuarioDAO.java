package com.kazuki_turismo.api_kazukiTurismo.dao;
import com.kazuki_turismo.api_kazukiTurismo.config.DatabaseConfig;
import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequestMapping("/api/usuarios")
@Tag(name = "Controlador de Usuarios", description = "Gestion de Usuarios")
public class UsuarioDAO {

    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, correo, contrasena, rol_usuario FROM usuario";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRolUsuario(rs.getString("rol_usuario"));
                usuarios.add(u);
            }
        }
        return usuarios;
    }

    public void insertar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, correo, contrasena, rol_usuario) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getContrasena());
            ps.setString(4, u.getRolUsuario());
            ps.executeUpdate();
        }
    }

    public void actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuario SET nombre=?, correo=?, contrasena=?, rol_usuario=? WHERE id_usuario=?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getContrasena());
            ps.setString(4, u.getRolUsuario());
            ps.setInt(5, u.getIdUsuario());
            ps.executeUpdate();
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public boolean existeId(int id) throws SQLException {
        String sql = "SELECT id_usuario FROM usuario WHERE id_usuario = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}