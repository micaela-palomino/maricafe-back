package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // productos con stock > 0. Esto me devuelve una lista de productos que tenga mas stick que el valor pasado (ordenado)
    List<Product> findByStockGreaterThan(int stock, Sort sort);

    // productos por categoría (usa categoryId) y con stock > 0. tambien los ordena
    List<Product> findByCategory_CategoryIdAndStockGreaterThan(Integer categoryId, int stock, Sort sort);
}
