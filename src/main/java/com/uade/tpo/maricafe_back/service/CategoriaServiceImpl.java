package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements ICategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
}