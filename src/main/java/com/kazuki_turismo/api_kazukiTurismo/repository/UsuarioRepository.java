package com.kazuki_turismo.api_kazukiTurismo.repository;

import com.kazuki_turismo.api_kazukiTurismo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}