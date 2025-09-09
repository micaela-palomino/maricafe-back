package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;
import com.uade.tpo.maricafe_back.service.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // 3.1 Listar productos (excluye sin stock)
    @GetMapping("/filterPrices")
    public List<Product> getAllProductsFiltered(
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

    //3.4 Obtener productos por cualquiera de sus atributos (title, description, priceMax), teniendo en cuents que tal vez solo pasa uno de sus atributos
    @GetMapping("/attributes")
    public List<ProductDTO> getProductsByAttributes(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double priceMax
    ) {
        return productService.findProductsByAttributes(title, description, priceMax);
    }

    //EP1 TOMI: 3.5 GET /categories/{id}/products?sort=price,asc|desc
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(required = false) String sort) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId, sort));
    }

    //EP4 TOMI: 3.8 GET /products?sort=price,asc|desc (la query que me pusieron en trello)
    @GetMapping
    public ResponseEntity<List<ProductDTO>> listProducts(@RequestParam(required = false) String sort) {
        return ResponseEntity.ok(productService.listProductsSortedByPrice(sort));
    }

}
