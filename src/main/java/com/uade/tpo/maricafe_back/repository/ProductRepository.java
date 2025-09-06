package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Category;
import com.uade.tpo.maricafe_back.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // 1. Buscar productos disponibles con filtros opcionales
    @Query("SELECT p FROM Product p WHERE p.stock > 0 "
            + "AND (:q IS NULL OR LOWER(p.tittle) LIKE %:q%) "
            + "AND (:priceMin IS NULL OR p.price >= :priceMin) "
            + "AND (:priceMax IS NULL OR p.price <= :priceMax)")
    List<Product> findAvailableProducts(String q, Double priceMin, Double priceMax);

    // 2. Buscar producto por id y stock mayor a 0
    Optional<Product> findByProductIdAndStockGreaterThan(Integer productId, int stockIsGreaterThan);

    // 3. Buscar imágenes por id de producto
    @Query("SELECT i.url FROM Image i WHERE i.product.productId = :id")
    List<String> findImagesByProductId(Integer id);

    // 4. Buscar productos por categoría y stock mayor a 0
    List<Product> findByCategoryAndStockGreaterThan(Category category, int stock);

    // 5. Buscar productos por categoryId y stock mayor a 0 (para DTO). Tambien los ordena
    List<Product> findByCategory_CategoryIdAndStockGreaterThan(Integer categoryId, int stock, Sort sort);

    // 6. Buscar productos con stock mayor a 0 y orden. Esto me devuelve una lista de productos que tenga mas stick que el valor pasado (ordenado)
    List<Product> findByStockGreaterThan(int stock, Sort sort);

    boolean findByProductId(Integer productId);
}
