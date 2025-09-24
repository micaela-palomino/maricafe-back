package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.Image;
import com.uade.tpo.maricafe_back.entity.dto.ImageDTO;
import com.uade.tpo.maricafe_back.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.Base64;

@RestController
@RequestMapping("/images")
@CrossOrigin // si querés, podés moverlo a config global de CORS
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<ImageResponse> displayImage(@RequestParam("id") long id) throws SQLException {
        Image image = imageService.viewById(id);              // image.getData() -> byte[]
        String base64 = Base64.getEncoder().encodeToString(image.getData());
        return ResponseEntity.ok(ImageResponse.builder()
                .id(id)
                .file(base64)
                .build());
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> addImagePost(@RequestParam("file") MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();                       // Esto puede lanzar IOException
        Long createdId = imageService.create(
                Image.builder().data(bytes).build()
        );
        return ResponseEntity.ok("created:" + createdId);
    }
}