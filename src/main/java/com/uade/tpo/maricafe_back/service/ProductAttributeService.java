package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.ProductAttribute;
import com.uade.tpo.maricafe_back.entity.ProductAttributeValue;
import com.uade.tpo.maricafe_back.entity.dto.ProductAttributeDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductAttributeValueDTO;
import com.uade.tpo.maricafe_back.repository.ProductAttributeRepository;
import com.uade.tpo.maricafe_back.repository.ProductAttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {

    private final ProductAttributeRepository attributeRepository;
    private final ProductAttributeValueRepository attributeValueRepository;

    // Attribute management
    public List<ProductAttributeDTO> getAttributesByCategory(Integer categoryId) {
        return attributeRepository.findByCategory_CategoryId(categoryId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductAttributeDTO> getAllAttributes() {
        return attributeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductAttributeDTO createAttribute(ProductAttributeDTO attributeDTO) {
        ProductAttribute attribute = ProductAttribute.builder()
                .name(attributeDTO.getName())
                .dataType(attributeDTO.getDataType())
                .description(attributeDTO.getDescription())
                .required(attributeDTO.isRequired())
                .selectOptions(attributeDTO.getSelectOptions() != null ? 
                    String.join(",", attributeDTO.getSelectOptions()) : null)
                .build();
        
        ProductAttribute saved = attributeRepository.save(attribute);
        return convertToDTO(saved);
    }

    // Attribute value management
    public List<ProductAttributeValueDTO> getAttributeValuesByProduct(Integer productId) {
        return attributeValueRepository.findByProduct_ProductId(productId)
                .stream()
                .map(this::convertValueToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductAttributeValueDTO setAttributeValue(Integer productId, Integer attributeId, String value) {
        // Check if value already exists
        var existingValue = attributeValueRepository
                .findByProduct_ProductIdAndAttribute_AttributeId(productId, attributeId);
        
        ProductAttributeValue attributeValue;
        if (existingValue.isPresent()) {
            attributeValue = existingValue.get();
            attributeValue.setValue(value);
        } else {
            attributeValue = ProductAttributeValue.builder()
                    .product(Product.builder().productId(productId).build())
                    .attribute(ProductAttribute.builder().attributeId(attributeId).build())
                    .value(value)
                    .build();
        }
        
        ProductAttributeValue saved = attributeValueRepository.save(attributeValue);
        return convertValueToDTO(saved);
    }

    @Transactional
    public void deleteAttributeValue(Integer productId, Integer attributeId) {
        attributeValueRepository.findByProduct_ProductIdAndAttribute_AttributeId(productId, attributeId)
                .ifPresent(attributeValueRepository::delete);
    }

    // Conversion methods
    private ProductAttributeDTO convertToDTO(ProductAttribute attribute) {
        return ProductAttributeDTO.builder()
                .attributeId(attribute.getAttributeId())
                .name(attribute.getName())
                .dataType(attribute.getDataType())
                .description(attribute.getDescription())
                .required(attribute.isRequired())
                .selectOptions(attribute.getSelectOptions() != null ? 
                    List.of(attribute.getSelectOptions().split(",")) : null)
                .categoryId(attribute.getCategory() != null ? attribute.getCategory().getCategoryId() : null)
                .build();
    }

    private ProductAttributeValueDTO convertValueToDTO(ProductAttributeValue attributeValue) {
        return ProductAttributeValueDTO.builder()
                .valueId(attributeValue.getValueId())
                .productId(attributeValue.getProduct().getProductId())
                .attribute(convertToDTO(attributeValue.getAttribute()))
                .value(attributeValue.getValue())
                .build();
    }
}
