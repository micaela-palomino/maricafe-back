package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {

    // Find attributes by category
    List<ProductAttribute> findByCategory_CategoryId(Integer categoryId);

    // Find attributes by name
    List<ProductAttribute> findByNameContainingIgnoreCase(String name);

    // Find required attributes for a category
    @Query("SELECT pa FROM ProductAttribute pa WHERE pa.category.categoryId = :categoryId AND pa.required = true")
    List<ProductAttribute> findRequiredAttributesByCategory(@Param("categoryId") Integer categoryId);

    // Find attributes by data type
    List<ProductAttribute> findByDataType(String dataType);
}
