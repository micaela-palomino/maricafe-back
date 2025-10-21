package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Image;
import com.uade.tpo.maricafe_back.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // 1) Buscar productos disponibles con filtros opcionales (agrego @Param)
    @Query("""
           SELECT p FROM Product p
           WHERE p.stock > 0
             AND (:q IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :q, '%')))
             AND (:priceMin IS NULL OR p.price >= :priceMin)
             AND (:priceMax IS NULL OR p.price <= :priceMax)
           """)
    List<Product> findAvailableProducts(@Param("q") String q,
                                        @Param("priceMin") Double priceMin,
                                        @Param("priceMax") Double priceMax);

    // 1-b) Buscar TODOS los productos con filtros opcionales (para ADMIN)
    @Query("""
           SELECT p FROM Product p
           WHERE (:q IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :q, '%')))
             AND (:priceMin IS NULL OR p.price >= :priceMin)
             AND (:priceMax IS NULL OR p.price <= :priceMax)
           """)
    List<Product> findAllProducts(@Param("q") String q,
                                  @Param("priceMin") Double priceMin,
                                  @Param("priceMax") Double priceMax);

    // 2) Buscar producto por id y stock mayor a 0 (el campo es productId)
    Optional<Product> findByProductIdAndStockGreaterThan(Integer productId, int stockIsGreaterThan);

    // 3) **ELIMINADO** el método que hacía SELECT i.url ... (no existe 'url' en Image).

    // 3-b) Si querés traer IMÁGENES por productId desde ProductRepository (con join)
    @Query("SELECT i FROM Product p JOIN p.images i WHERE p.productId = :productId")
    List<Image> findImagesByProductId(@Param("productId") Integer productId);

    // 4) Buscar por atributos (agrego @Param y LOWER/CONCAT)
    @Query("""
           SELECT p FROM Product p
           WHERE p.stock > 0
             AND (:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%')))
             AND (:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%')))
             AND (:priceMax IS NULL OR p.price <= :priceMax)
           """)
    List<Product> findByAttributes(@Param("title") String title,
                                   @Param("description") String description,
                                   @Param("priceMax") Double priceMax);

    // 4-b) Buscar TODOS los productos por atributos (para ADMIN)
    @Query("""
           SELECT p FROM Product p
           WHERE (:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%')))
             AND (:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%')))
             AND (:priceMax IS NULL OR p.price <= :priceMax)
           """)
    List<Product> findAllByAttributes(@Param("title") String title,
                                      @Param("description") String description,
                                      @Param("priceMax") Double priceMax);

    // 5) Por categoryId y con stock, ordenado
    List<Product> findByCategory_CategoryIdAndStockGreaterThan(Integer categoryId, int stock, Sort sort);

    // 5-b) Por categoryId (TODOS los productos, para ADMIN)
    List<Product> findByCategory_CategoryId(Integer categoryId, Sort sort);

    // 6) Stock > X con orden
    List<Product> findByStockGreaterThan(int stock, Sort sort);

    // 7) **Corregido**: si querías chequear existencia, debe ser 'existsBy...'
    boolean existsByProductId(Integer productId);
}
