package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
}
