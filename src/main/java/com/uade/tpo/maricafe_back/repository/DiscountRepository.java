package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    Optional<Discount> findTopByProduct_ProductIdOrderByDiscountIdDesc(Integer productId);
}
