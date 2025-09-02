package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.dto.DiscountDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;

import java.util.List;

public interface IProductService {
    // 3.5 productos por categoría (con stock)
    List<ProductDTO> getProductsByCategory(Integer categoryId, String sortParam);

    // 3.6 crear o aplicar descuento a un producto
    DiscountDTO createDiscount(Integer productId, double percentage);

    // 3.7 actualizar descuento existente
    DiscountDTO updateDiscount(Integer discountId, double percentage);

    // 3.8 listar productos (con stock) ordenados por precio (asc/desc)
    List<ProductDTO> listProductsSortedByPrice(String sortParam);

}
