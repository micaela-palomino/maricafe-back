package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.controllers.ImageResponse;

import java.util.List;

public interface ImageService {
    // 3.3 obtener imágenes del producto por id
    List<String> findImagesByProductId(Integer id);
    // 3.4 obtener imágenes del producto con IDs
    List<ImageResponse> findImagesWithIdsByProductId(Integer id);
    Long createForProduct(byte[] imageBytes, Integer productId);
    Long createForProduct(byte[] imageBytes, Integer productId, Integer imageOrder);
    void deleteById(Long id);
}


