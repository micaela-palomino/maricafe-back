package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Integer> {
    // Ejemplo: buscar descuentos por porcentaje
    // List<Descuento> findByPorcentajeDescuentoGreaterThan(double porcentaje);
}
