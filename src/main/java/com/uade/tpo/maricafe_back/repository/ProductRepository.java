package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByTitulo(String tittle);
}
