package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.Image;
import com.uade.tpo.maricafe_back.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@CrossOrigin // si querés, podés moverlo a config global de CORS
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService imageService;

    // 3.3 Obtener imágenes del producto por id
    @GetMapping("/{productId}")
    public ResponseEntity<List<String>> getProductImages(@PathVariable Integer productId) {
        return ResponseEntity.ok(imageService.findImagesByProductId(productId));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> addImagePost(@RequestParam("file") MultipartFile file,
                                               @RequestParam("productId") Integer productId) throws Exception {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo no puede estar vacío");
        }
        
        byte[] bytes = file.getBytes();
        Long createdId = imageService.createForProduct(bytes, productId);
        return ResponseEntity.ok("created:" + createdId);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId) {
        try {
            imageService.deleteById(imageId);
            return ResponseEntity.ok("Imagen eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}