package com.uade.tpo.maricafe_back.service;

import java.util.List;

public interface ImageService {
    // 3.3 obtener imágenes del producto por id
    List<String> findImagesByProductId(Integer id);
    Long createForProduct(byte[] imageBytes, Integer productId);
    Long createForProduct(byte[] imageBytes, Integer productId, Integer imageOrder);
    void deleteById(Long id);
}


