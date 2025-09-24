package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.dto.CreateProductDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;

import java.util.List;


public interface IProductService {

    // 3.1 listar productos (con stock) con filtros opcionales
    List<ProductDTO> findAvailableProducts(String q, Double priceMin, Double priceMax);

    // 3.2 obtener producto por id (si tiene stock)
    ProductDTO findByIdAndAvailable(Integer id);

    // 3.2 obtener producto por id para admin
    ProductDTO findById(Integer id);

    // 3.3 obtener imágenes del producto por id
    List<String> findImagesByProductId(Integer id);

    // 3.4 obtener productos por categoría (con stock)
    List<ProductDTO> findProductsByAttributes(String title, String description, Double priceMax);

    // 3.5 productos por categoría (con stock)
    List<ProductDTO> getProductsByCategory(Integer categoryId, String sortParam);


    // 3.8 listar productos (con stock) ordenados por precio (asc/desc)
    List<ProductDTO> listProductsSortedByPrice(String sortParam);

    // 4.1 crear producto
    ProductDTO createProduct(CreateProductDTO dto);

    // 4.2 actualizar producto
    ProductDTO updateProduct(Integer productId, CreateProductDTO dto);

    // 4.3 eliminar producto
    void deleteProduct(Integer productId);
}

