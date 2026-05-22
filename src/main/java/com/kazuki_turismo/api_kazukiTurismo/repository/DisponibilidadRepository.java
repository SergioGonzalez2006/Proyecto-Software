package com.kazuki_turismo.api_kazukiTurismo.repository;

import com.kazuki_turismo.api_kazukiTurismo.model.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Integer> {
}