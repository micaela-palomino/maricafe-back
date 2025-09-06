package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Category;
import com.uade.tpo.maricafe_back.entity.Discount;
import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.dto.DiscountDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;
import com.uade.tpo.maricafe_back.exceptions.ResourceNotFoundException;
import com.uade.tpo.maricafe_back.repository.CategoryRepository;
import com.uade.tpo.maricafe_back.repository.DiscountRepository;
import com.uade.tpo.maricafe_back.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, DiscountRepository discountRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository; //consultas y operaciones sobre productos
        this.discountRepository = discountRepository; //consultas y operaciones sobre descuentos
        this.categoryRepository = categoryRepository; //para validar categorías existentes
        this.modelMapper = modelMapper; //para convertir entidades (ej: Product) a dto (ProductDTO)
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
        return modelMapper.map(p, ProductDTO.class);
    }
    private DiscountDTO toDTO(Discount d) {
        DiscountDTO dto = new DiscountDTO();
        dto.setDiscountId(d.getDiscountId());
        dto.setProductId(d.getProduct().getProductId());
        dto.setDiscountPercentage(d.getDiscountPercentage());
        return dto;
    }

    //3.1 buscar productos con stock y filtros opcionales
    @Override
    public List<Product> findAvailableProducts(String q, Double priceMin, Double priceMax) {
        if (priceMin != null && priceMax != null && priceMin > priceMax) {
            throw new IllegalArgumentException("priceMin no puede ser mayor a priceMax");
        }
        if (q != null && !q.isBlank()) {
            q = q.toLowerCase();
        }
        return productRepository.findAvailableProducts(q, priceMin, priceMax);
    }

    //3.2 buscar producto por id y que tenga stock
    @Override
    public Product findByIdAndAvailable(Integer id) {
        return productRepository.findByProductIdAndStockGreaterThan(id, 0)
                .orElseThrow(() -> new IllegalArgumentException("El producto: " + id + " no fue encontrado o no tiene stock"));
    }

    //3.3 buscar imagenes por id de producto
    @Override
    public List<String> findImagesByProductId(Integer id) {
        //validar que el producto exista (opcional si queremos 404)
        if (!productRepository.findByProductId(id)) {
            throw new ResourceNotFoundException("Product with id " + id + " not found");
        }
        return productRepository.findImagesByProductId(id);
    }

    //3.4 buscar productos por categoría (con stock)
    @Override
    public List<Product> findByCategory (Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("La categoria: " + categoryId + " no fue encontrada"));
        return productRepository.findByCategoryAndStockGreaterThan(category, 0);
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

    //3.6 crear o aplicar descuento a un producto
    @Override
    public DiscountDTO createDiscount(Integer productId, double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("discountPercentage debe estar entre 0 y 100");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("El producto: " + productId + " no fue encontrado"));

        Discount discount = Discount.builder()
                .product(product)
                .discountPercentage(percentage)
                .build();

        discount = discountRepository.save(discount);
        return toDTO(discount);
    }

    //3.7 actualizar descuento existente
    @Override
    public DiscountDTO updateDiscount(Integer discountId, double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("discountPercentage debe estar entre 0 y 100");
        }
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new IllegalArgumentException("El descuento: " + discountId + " no fue encontrada"));

        discount.setDiscountPercentage(percentage);
        discount = discountRepository.save(discount);
        return toDTO(discount);
    }
}
