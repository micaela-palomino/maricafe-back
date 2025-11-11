package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Category;
import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.Discount;
import com.uade.tpo.maricafe_back.entity.ProductAttributeValue;
import com.uade.tpo.maricafe_back.entity.dto.CreateProductDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductAttributeDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductAttributeValueDTO;
import com.uade.tpo.maricafe_back.exceptions.ResourceNotFoundException;
import com.uade.tpo.maricafe_back.repository.CategoryRepository;
import com.uade.tpo.maricafe_back.repository.ProductRepository;
import com.uade.tpo.maricafe_back.repository.DiscountRepository;
import com.uade.tpo.maricafe_back.repository.ProductAttributeValueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DiscountRepository discountRepository;
    private final ProductAttributeValueRepository attributeValueRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, DiscountRepository discountRepository, ProductAttributeValueRepository attributeValueRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.discountRepository = discountRepository;
        this.attributeValueRepository = attributeValueRepository;
        this.modelMapper = modelMapper;
    }

    //esto me permite ordenar productos por precio asc/desc desde el endpoint. traduce un string (ej: price,asc) en un objeto sort
    private Sort parseSortByPrice(String sortParam) {
        // soporta: null, "price,asc", "price,desc"
        String dir = "asc";
        if (sortParam != null) {
            String[] parts = sortParam.split(",");
            if (parts.length == 2 && "desc".equalsIgnoreCase(parts[1])) dir = "desc";
        }
        return "desc".equalsIgnoreCase(dir) ? Sort.by("price").descending() : Sort.by("price").ascending();
    }

    //convierte entidades a dto para no exponer directamente a la BD
    private ProductDTO toDTO(Product p) {
        ProductDTO dto = modelMapper.map(p, ProductDTO.class);
        
        // Enriquecer con información de descuentos si existe un descuento asociado
        Optional<Discount> lastDiscount = discountRepository.findTopByProduct_ProductIdOrderByDiscountIdDesc(p.getProductId());
        if (lastDiscount.isPresent()) {
            Discount discount = lastDiscount.get();
            dto.setDiscountId(discount.getDiscountId());
            dto.setDiscountPercentage(discount.getDiscountPercentage());
            // Calcular el precio con descuento (el precio en BD es el precio original)
            double originalPrice = p.getPrice();
            double discountedPrice = originalPrice * (1 - discount.getDiscountPercentage() / 100);
            dto.setNewPrice(discountedPrice);
        } else {
            dto.setDiscountId(null);
            dto.setDiscountPercentage(null);
            dto.setNewPrice(null);
        }
        
        // Load attribute values for the product
        List<ProductAttributeValueDTO> attributeValues = attributeValueRepository
                .findByProduct_ProductId(p.getProductId())
                .stream()
                .map(this::convertValueToDTO)
                .collect(Collectors.toList());
        dto.setAttributes(attributeValues);
        
        return dto;
    }

    // Convert ProductAttributeValue to DTO
    private ProductAttributeValueDTO convertValueToDTO(ProductAttributeValue attributeValue) {
        return ProductAttributeValueDTO.builder()
                .valueId(attributeValue.getValueId())
                .productId(attributeValue.getProduct().getProductId())
                .attribute(ProductAttributeDTO.builder()
                        .attributeId(attributeValue.getAttribute().getAttributeId())
                        .name(attributeValue.getAttribute().getName())
                        .dataType(attributeValue.getAttribute().getDataType())
                        .description(attributeValue.getAttribute().getDescription())
                        .required(attributeValue.getAttribute().isRequired())
                        .selectOptions(attributeValue.getAttribute().getSelectOptions() != null ? 
                            List.of(attributeValue.getAttribute().getSelectOptions().split(",")) : null)
                        .categoryId(attributeValue.getAttribute().getCategory() != null ? 
                            attributeValue.getAttribute().getCategory().getCategoryId() : null)
                        .build())
                .value(attributeValue.getValue())
                .build();
    }

    //3.1 buscar productos con filtros opcionales (role-based)
    @Override
    public List<ProductDTO> findProducts(String q, Double priceMin, Double priceMax, boolean isAdmin) {
        if (priceMin != null && priceMax != null && priceMin > priceMax) {
            throw new IllegalArgumentException("priceMin no puede ser mayor a priceMax");
        }
        if (q != null && !q.isBlank()) {
            q = q.toLowerCase();
        }
        
        List<Product> products;
        if (isAdmin) {
            // Admin sees all products
            products = productRepository.findAllProducts(q, priceMin, priceMax);
        } else {
            // User sees only products with stock
            products = productRepository.findAvailableProducts(q, priceMin, priceMax);
        }
        
        return products.stream().map(this::toDTO).toList();
    }

    //3.2 obtener producto por id (role-based)
    @Override
    public ProductDTO findById(Integer id, boolean isAdmin) {
        if (isAdmin) {
            // Admin can see any product
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encuentra producto con id: " + id));
            return toDTO(product);
        } else {
            // User can only see products with stock
            Product product = productRepository.findByProductIdAndStockGreaterThan(id, 0)
                    .orElseThrow(() -> new IllegalArgumentException("El producto con id: " + id + " no fue encontrado o no tiene stock"));
            return toDTO(product);
        }
    }

    //3.4 buscar productos por atributos (role-based)
    @Override
    public List<ProductDTO> findProductsByAttributes(String title, String description, Double priceMax, boolean isAdmin) {
        List<Product> products;
        if (isAdmin) {
            // Admin sees all products
            products = productRepository.findAllByAttributes(title, description, priceMax);
        } else {
            // User sees only products with stock
            products = productRepository.findByAttributes(title, description, priceMax);
        }
        
        return products.stream().map(this::toDTO).toList();
    }

    //3.8 listar productos ordenados por precio (role-based)
    @Override
    public List<ProductDTO> listProductsSortedByPrice(String sortParam, boolean isAdmin) {
        Sort sort = parseSortByPrice(sortParam);
        
        List<Product> products;
        if (isAdmin) {
            // Admin sees all products
            products = productRepository.findAll(sort);
        } else {
            // User sees only products with stock
            products = productRepository.findByStockGreaterThan(0, sort);
        }
        
        return products.stream().map(this::toDTO).toList();
    }

    @Override
    public List<ProductDTO> getProductsWithAttributes(String sortParam) {
        Sort sort = parseSortByPrice(sortParam);
        // Get products with stock and include their attributes
        return productRepository.findByStockGreaterThan(0, sort)
                .stream().map(this::toDTO).toList();
    }

    @Override
    public List<ProductDTO> getProductsFilteredByAttributes(String sortParam, Integer categoryId, String attributeFilters) {
        Sort sort = parseSortByPrice(sortParam);
        
        // Get all products with stock
        List<Product> allProducts = productRepository.findByStockGreaterThan(0, sort);
        
        // Filter by category if specified
        if (categoryId != null) {
            allProducts = allProducts.stream()
                    .filter(product -> product.getCategory().getCategoryId().equals(categoryId))
                    .toList();
        }
        
        // Filter by attributes if specified
        if (attributeFilters != null && !attributeFilters.trim().isEmpty()) {
            try {
                // Parse attribute filters (format: "attributeId1:value1,attributeId2:value2")
                String[] filterPairs = attributeFilters.split(",");
                for (String pair : filterPairs) {
                    String[] parts = pair.split(":");
                    if (parts.length == 2) {
                        Integer attributeId = Integer.parseInt(parts[0].trim());
                        String filterValue = parts[1].trim();
                        
                        // Filter products that have this attribute with this value
                        allProducts = allProducts.stream()
                                .filter(product -> {
                                    return attributeValueRepository.findByProduct_ProductId(product.getProductId())
                                            .stream()
                                            .anyMatch(attrValue -> 
                                                attrValue.getAttribute().getAttributeId().equals(attributeId) &&
                                                attrValue.getValue().equals(filterValue)
                                            );
                                })
                                .toList();
                    }
                }
            } catch (Exception e) {
                // If parsing fails, return all products
                System.err.println("Error parsing attribute filters: " + e.getMessage());
            }
        }
        
        return allProducts.stream().map(this::toDTO).toList();
    }

    //3.5 obtener productos por categoría (role-based)
    @Override
    public List<ProductDTO> getProductsByCategory(Integer categoryId, String sortParam, boolean isAdmin) {
        //validar categoría (opcional si queremos 404)
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("La categoria: " + categoryId + " no fue encontrada"));

        Sort sort = parseSortByPrice(sortParam);
        
        List<Product> products;
        if (isAdmin) {
            // Admin sees all products in category
            products = productRepository.findByCategory_CategoryId(categoryId, sort);
        } else {
            // User sees only products with stock in category
            products = productRepository.findByCategory_CategoryIdAndStockGreaterThan(categoryId, 0, sort);
        }
        
        return products.stream().map(this::toDTO).toList();
    }

    // 4.1 crear producto (solo ADMIN)
    @Override
    public ProductDTO createProduct(CreateProductDTO dto) {
        // Validar que la categoría existe
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("La categoría con id: " + dto.getCategoryId() + " no fue encontrada"));

        // Validar datos del producto
        if (dto.getPrice() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (dto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }

        // Crear producto
        Product product = Product.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(category)
                .stock(dto.getStock())
                .build();

        product = productRepository.save(product);
        return toDTO(product);
    }

    // 4.2 actualizar producto (solo ADMIN)
    @Override
    public ProductDTO updateProduct(Integer productId, CreateProductDTO dto) {
        // Validar que el producto existe
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("El producto con id: " + productId + " no fue encontrado"));

        // Validar que la categoría existe (si se proporciona)
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("La categoría con id: " + dto.getCategoryId() + " no fue encontrada"));
            product.setCategory(category);
        }

        // Validar datos del producto
        if (dto.getPrice() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (dto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (dto.getTitle() != null && dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        // Actualizar campos (solo si se proporcionan)
        if (dto.getTitle() != null) {
            product.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getPrice() >= 0) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getStock() >= 0) {
            product.setStock(dto.getStock());
        }

        product = productRepository.save(product);
        return toDTO(product);
    }

    // 4.3 eliminar producto (solo ADMIN)
    @Override
    public void deleteProduct(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("El producto con id: " + productId + " no fue encontrado");
        }
        try {
            productRepository.deleteById(productId);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("No se puede eliminar el producto porque está asociado a al menos una orden.");
        }
    }
}
