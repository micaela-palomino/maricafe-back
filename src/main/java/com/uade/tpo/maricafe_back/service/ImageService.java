package com.uade.tpo.maricafe_back.service;

import java.util.List;

public interface ImageService {
    // 3.3 obtener imágenes del producto por id
    List<String> findImagesByProductId(Integer id);
    Long createForProduct(byte[] imageBytes, Integer productId);
    void deleteById(Long id);
}


