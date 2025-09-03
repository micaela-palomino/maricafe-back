package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.dto.CreateUpdateDiscountDTO;
import com.uade.tpo.maricafe_back.entity.dto.DiscountDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;
import com.uade.tpo.maricafe_back.service.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // 3.1 Listar productos (excluye sin stock)
    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax
    ) {
        return productService.findAvailableProducts(q, priceMin, priceMax);
    }

    // 3.2 Obtener producto por id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.findByIdAndAvailable(id);
    }

    // 3.3 Obtener imágenes del producto por id
    @GetMapping("/{id}/images")
    public List<String> getProductImages(@PathVariable Integer id) {
        return productService.findImagesByProductId(id);
    }

    //3.4 Obtener productos por categoría
    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Integer categoryId) {
        return productService.findByCategory(categoryId);
    }

    //EP4 TOMI: 3.8 GET /products?sort=price,asc|desc (la query que me pusieron en trello)
    @GetMapping
    public ResponseEntity<List<ProductDTO>> listProducts(@RequestParam(required = false) String sort) {
        return ResponseEntity.ok(productService.listProductsSortedByPrice(sort));
    }

    //EP2 TOMI: 3.6 POST /products/{id}/discounts
    @PostMapping("/{productId}/discounts")
    public ResponseEntity<DiscountDTO> createDiscount(
            @PathVariable Integer productId,
            @RequestBody CreateUpdateDiscountDTO dto) {
        DiscountDTO saved = productService.createDiscount(productId, dto.getDiscountPercentage());
        return ResponseEntity.created(URI.create("/discounts/" + saved.getDiscountId())).body(saved);
    }
}
