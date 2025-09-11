package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Order;
import com.uade.tpo.maricafe_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
}
