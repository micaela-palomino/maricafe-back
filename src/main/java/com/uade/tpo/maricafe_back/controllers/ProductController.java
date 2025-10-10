package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.dto.ApiResponseDTO;
import com.uade.tpo.maricafe_back.entity.dto.CreateProductDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;
import com.uade.tpo.maricafe_back.service.IProductService;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/filterPrices")
    public List<ProductDTO> getAllProductsFiltered(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax
    ) {
        return productService.findAvailableProducts(title, priceMin, priceMax);
    }

    // 3.2 Obtener producto por id
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Integer id) {
        return productService.findByIdAndAvailable(id);
    }

    // 3.2 Obtener producto por id para admins
    @GetMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductByIdAdmin(@PathVariable Integer id) {
        return productService.findById(id);
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

    // 4.1 POST /products - Crear producto (solo ADMIN)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponseDTO<ProductDTO>> createProduct(@RequestBody CreateProductDTO dto) {
        ProductDTO saved = productService.createProduct(dto);
        return ResponseEntity
                .created(URI.create("/products/" + saved.getIdProduct()))
                .body(ApiResponseDTO.success("Producto creado con éxito", saved));
    }

    // 4.2 PUT /products/{id} - Actualizar producto (solo ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ProductDTO>> updateProduct(
            @PathVariable Integer id,
            @RequestBody CreateProductDTO dto) {
        ProductDTO updated = productService.updateProduct(id, dto);
        return ResponseEntity.ok(ApiResponseDTO.success("Producto actualizado con éxito", updated));
    }

    // 4.3 DELETE /products/{id} - Eliminar producto (solo ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Producto eliminado con éxito"));
    }

}
