package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Ejemplo: buscar ordenes por fecha
    // List<Orden> findByFechaOrden(String fechaOrden);
}
