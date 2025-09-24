package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Image;

public interface ImageService {
    Long create(Image image);
    Image viewById(Long id);
}


