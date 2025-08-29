package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    // Ejemplo: buscar ordenes por fecha
    // List<Orden> findByFechaOrden(String fechaOrden);
}
