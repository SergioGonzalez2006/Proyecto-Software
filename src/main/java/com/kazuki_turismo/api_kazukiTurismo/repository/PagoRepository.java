package com.kazuki_turismo.api_kazukiTurismo.repository;

import com.kazuki_turismo.api_kazukiTurismo.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
}