package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Category;
import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.Discount;
import com.uade.tpo.maricafe_back.entity.dto.CreateProductDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;
import com.uade.tpo.maricafe_back.exceptions.ResourceNotFoundException;
import com.uade.tpo.maricafe_back.repository.CategoryRepository;
import com.uade.tpo.maricafe_back.repository.ProductRepository;
import com.uade.tpo.maricafe_back.repository.DiscountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, DiscountRepository discountRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.discountRepository = discountRepository;
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
            double percentage = lastDiscount.get().getDiscountPercentage();
            dto.setDiscountPercentage(percentage);
            // El precio actual del producto ya es el precio con descuentos en este modelo
            double newPrice = p.getPrice();
            dto.setNewPrice(newPrice);
        } else {
            dto.setDiscountPercentage(null);
            dto.setNewPrice(null);
        }
        return dto;
    }


    //3.1 buscar productos con stock y filtros opcionales
    @Override
    public List<ProductDTO> findAvailableProducts(String q, Double priceMin, Double priceMax) {

        if (priceMin != null && priceMax != null && priceMin > priceMax) {
            throw new IllegalArgumentException("priceMin no puede ser mayor a priceMax");
        }
        if (q != null && !q.isBlank()) {
            q = q.toLowerCase();
        }
        return productRepository.findAvailableProducts(q, priceMin, priceMax)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    //3.2 buscar producto por id y que tenga stock
    @Override
    public ProductDTO findByIdAndAvailable(Integer id) {
        Product product = productRepository.findByProductIdAndStockGreaterThan(id, 0)
                .orElseThrow(() -> new IllegalArgumentException("El producto con id: " + id + " no fue encontrado o no tiene stock"));
        return toDTO(product);
    }

    @Override
    public ProductDTO findById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra producto con id: " + id));
        return toDTO(product);
    }

    //3.4 buscar productos por categoría (con stock)
    @Override
    public List<ProductDTO> findProductsByAttributes(String title, String description, Double priceMax) {
        return productRepository.findByAttributes(title, description, priceMax)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    //3.8 listar productos (con stock) ordenados por precio (asc/desc)
    @Override
    public List<ProductDTO> listProductsSortedByPrice(String sortParam) {
        Sort sort = parseSortByPrice(sortParam);
        //no mostrar productos sin stock
        return productRepository.findByStockGreaterThan(0, sort)
                .stream().map(this::toDTO).toList();
    }

    //3.5 obtener productos por categoría (con stock) y ordenados por precio (asc/desc)
    @Override
    public List<ProductDTO> getProductsByCategory(Integer categoryId, String sortParam) {
        //validar categoría (opcional si queremos 404)
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("La categoria: " + categoryId + " no fue encontrada"));

        Sort sort = parseSortByPrice(sortParam);
        return productRepository
                .findByCategory_CategoryIdAndStockGreaterThan(categoryId, 0, sort)
                .stream().map(this::toDTO).toList();
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
        productRepository.deleteById(productId);
    }
}
