package com.kazuki_turismo.api_kazukiTurismo.repository;

import com.kazuki_turismo.api_kazukiTurismo.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    
}