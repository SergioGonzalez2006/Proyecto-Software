package com.kazuki_turismo.api_kazukiTurismo.repository;

import com.kazuki_turismo.api_kazukiTurismo.entity.Usuario;
import com.kazuki_turismo.api_kazukiTurismo.util.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository //En esta parte "clase" se accede a los datos
public class UsuarioRepository {

    @Autowired //inyección de dependencias o Spring que inyecta la conexión
    private DatabaseConnection databaseConnection;

    //Trae todos los usuarios de la base de datos

    public List<Usuario> obtenerUsuarios() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT id_usuario, nombre, correo, contrasena, rol_usuario FROM usuario";

        try (
            Connection conn = databaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario")); //Este lee la columna y la guarda en el objeto
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setRolUsuario(rs.getString("rol_usuario"));

                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }
}

//Avance Julissa 4-4-26 - proceso: Controller → Service → Repository → Database  (Se crea Repository)