package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Image;
import com.uade.tpo.maricafe_back.repository.ImageRepository;
import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public List<String> findImagesByProductId(Integer productId) {
        // Validar que el producto exista (sin restricción de stock para permitir admin ver productos sin stock)
        productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "El producto con id: " + productId + " no fue encontrado"));

        // Trae List<Image> desde el repo y lo convierte a Base64 (List<String>)
        return productRepository.findImagesByProductId(productId).stream()
                .map(img -> Base64.getEncoder().encodeToString(img.getData()))
                .toList();
    }

    @Override
    public Long createForProduct(byte[] imageBytes, Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Producto " + productId + " no encontrado"));

        Image image = Image.builder()
                .data(imageBytes)
                .product(product)
                .build();
        return imageRepository.save(image).getId();
    }

    @Override
    public void deleteById(Long imageId) {
        if (!imageRepository.existsById(imageId)) {
            throw new EntityNotFoundException("Imagen con id: " + imageId + " no encontrada");
        }
        imageRepository.deleteById(imageId);
    }
}

