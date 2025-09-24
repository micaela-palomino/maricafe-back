package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Image;
import com.uade.tpo.maricafe_back.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Long create(Image image) {
        return imageRepository.save(image).getId();
    }

    @Override
    public Image viewById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image " + id + " not found"));
    }
}

