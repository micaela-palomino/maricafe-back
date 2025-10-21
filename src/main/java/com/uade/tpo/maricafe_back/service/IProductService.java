package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.dto.CreateProductDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;

import java.util.List;


public interface IProductService {

    // 3.1 listar productos con filtros opcionales (role-based: USER sees only with stock, ADMIN sees all)
    List<ProductDTO> findProducts(String q, Double priceMin, Double priceMax, boolean isAdmin);

    // 3.2 obtener producto por id (role-based: USER only if available, ADMIN always)
    ProductDTO findById(Integer id, boolean isAdmin);

    // 3.4 obtener productos por atributos (role-based: USER only with stock, ADMIN sees all)
    List<ProductDTO> findProductsByAttributes(String title, String description, Double priceMax, boolean isAdmin);

    // 3.5 productos por categoría (role-based: USER only with stock, ADMIN sees all)
    List<ProductDTO> getProductsByCategory(Integer categoryId, String sortParam, boolean isAdmin);

    // 3.8 listar productos ordenados por precio (role-based: USER only with stock, ADMIN sees all)
    List<ProductDTO> listProductsSortedByPrice(String sortParam, boolean isAdmin);

    // Get products with attributes for filtering
    List<ProductDTO> getProductsWithAttributes(String sortParam);

    // Get products filtered by attributes
    List<ProductDTO> getProductsFilteredByAttributes(String sortParam, Integer categoryId, String attributeFilters);

    // 4.1 crear producto
    ProductDTO createProduct(CreateProductDTO dto);

    // 4.2 actualizar producto
    ProductDTO updateProduct(Integer productId, CreateProductDTO dto);

    // 4.3 eliminar producto
    void deleteProduct(Integer productId);
}

