package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Integer> {

    // Find all attribute values for a product
    List<ProductAttributeValue> findByProduct_ProductId(Integer productId);

    // Find attribute value for a specific product and attribute
    Optional<ProductAttributeValue> findByProduct_ProductIdAndAttribute_AttributeId(Integer productId, Integer attributeId);

    // Find all products that have a specific attribute value
    @Query("SELECT pav FROM ProductAttributeValue pav WHERE pav.attribute.attributeId = :attributeId AND pav.value = :value")
    List<ProductAttributeValue> findByAttributeAndValue(@Param("attributeId") Integer attributeId, @Param("value") String value);

    // Find attribute values by attribute name
    @Query("SELECT pav FROM ProductAttributeValue pav WHERE pav.attribute.name = :attributeName")
    List<ProductAttributeValue> findByAttributeName(@Param("attributeName") String attributeName);

    // Delete all attribute values for a product
    void deleteByProduct_ProductId(Integer productId);
}
