package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.dto.DiscountDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;

import java.util.List;

public interface IProductService {

    // 3.1 listar productos (con stock) con filtros opcionales
    List<Product> findAvailableProducts(String q, Double priceMin, Double priceMax);

    // 3.2 obtener producto por id (si tiene stock)
    Product findByIdAndAvailable(Integer id);

    // 3.3 obtener imágenes del producto por id
    List<String> findImagesByProductId(Integer id);

    // 3.4 obtener productos por categoría (con stock)
    List<Product> findByCategory(Integer categoryId);

    // 3.5 productos por categoría (con stock)
    List<ProductDTO> getProductsByCategory(Integer categoryId, String sortParam);

    // 3.6 crear o aplicar descuento a un producto
    DiscountDTO createDiscount(Integer productId, double percentage);

    // 3.7 actualizar descuento existente
    DiscountDTO updateDiscount(Integer discountId, double percentage);

    // 3.8 listar productos (con stock) ordenados por precio (asc/desc)
    List<ProductDTO> listProductsSortedByPrice(String sortParam);

}
