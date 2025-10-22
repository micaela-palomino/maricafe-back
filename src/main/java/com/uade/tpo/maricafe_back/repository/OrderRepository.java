package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Order;
import com.uade.tpo.maricafe_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
    
    // Métodos para filtrar solo órdenes activas
    List<Order> findByUserAndActiveTrue(User user);
    Optional<Order> findByOrderIdAndActiveTrue(Integer orderId);
    Optional<Order> findByOrderIdAndUserAndActiveTrue(Integer orderId, User user);
    List<Order> findByActiveTrue();
    
    // Métodos para administradores
    List<Order> findByActiveFalse();
}
