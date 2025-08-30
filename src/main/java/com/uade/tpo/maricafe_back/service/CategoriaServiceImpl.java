package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.cofigs.ModelMapperConfig;
import com.uade.tpo.maricafe_back.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements ICategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final ModelMapperConfig modelMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ModelMapperConfig modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }
}