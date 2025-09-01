package com.uade.tpo.maricafe_back.service;


import com.uade.tpo.maricafe_back.entity.Category;
import com.uade.tpo.maricafe_back.entity.dto.CategoryDTO;
import com.uade.tpo.maricafe_back.entity.dto.CreateCategoriaDTO;
import com.uade.tpo.maricafe_back.exceptions.CategoryDuplicateException;
import com.uade.tpo.maricafe_back.exceptions.ResourceNotFoundException;
import com.uade.tpo.maricafe_back.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoriaServiceImpl implements ICategoriaService {


    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoriaServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<CategoryDTO> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(category -> modelMapper.map(category, CategoryDTO.class));
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(categoria -> modelMapper.map(categoria, CategoryDTO.class));
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CreateCategoriaDTO dto) {
        boolean exists = categoryRepository.existsByNombre(dto.getNombre());
        if (exists) {
            throw new CategoryDuplicateException(dto.getNombre());
        }

        Category categoria = modelMapper.map(dto, Category.class);
        Category created = categoryRepository.save(categoria);
        return modelMapper.map(created, CategoryDTO.class);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría con id " + id + " no encontrada");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Integer id, CreateCategoriaDTO dto) {
        Category categoria = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con id " + id + " no encontrada"));

        // Verificar duplicados
        boolean exists = categoryRepository.existsByNombre(dto.getNombre());
        if (exists && !categoria.getNombre().equals(dto.getNombre())) {
            throw new CategoryDuplicateException(dto.getNombre());
        }

        categoria.setNombre(dto.getNombre());
        Category updated = categoryRepository.save(categoria);

        return modelMapper.map(updated, CategoryDTO.class);
    }
}
