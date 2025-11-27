package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Category;
import com.uade.tpo.maricafe_back.entity.dto.CategoryDTO;
import com.uade.tpo.maricafe_back.entity.dto.CreateCategoryDTO;
import com.uade.tpo.maricafe_back.exceptions.CategoryDuplicateException;
import com.uade.tpo.maricafe_back.exceptions.ResourceNotFoundException;
import com.uade.tpo.maricafe_back.repository.CategoryRepository;
import com.uade.tpo.maricafe_back.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
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
                .map(category -> modelMapper.map(category, CategoryDTO.class));
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CreateCategoryDTO dto) {
        if (dto.getName() != null && dto.getName().length() > 30) {
            throw new IllegalArgumentException("El nombre de la categoría no puede superar los 30 caracteres");
        }
        boolean exists = categoryRepository.existsByName(dto.getName());
        if (exists) {
            throw new CategoryDuplicateException(dto.getName());
        }

        Category category = modelMapper.map(dto, Category.class);
        Category created = categoryRepository.save(category);
        return modelMapper.map(created, CategoryDTO.class);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categora con id: " + id + " no encontrada"));

        boolean hasProducts = productRepository.existsByCategory_CategoryId(id);
        if (hasProducts) {
            throw new IllegalStateException("No se puede eliminar una categoria con Producto");
        }

        category.setActive(false);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Integer id, CreateCategoryDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con id: " + id + " no encontrada"));

        // Verificar duplicados
        boolean exists = categoryRepository.existsByName(dto.getName());
        if (exists && !category.getName().equals(dto.getName())) {
            throw new CategoryDuplicateException(dto.getName());
        }
        if (dto.getName() != null && dto.getName().length() > 30) {
            throw new IllegalArgumentException("El nombre de la categoría no puede superar los 30 caracteres");
        }

        category.setName(dto.getName());
        Category updated = categoryRepository.save(category);

        return modelMapper.map(updated, CategoryDTO.class);
    }

    @Override
    @Transactional
    public CategoryDTO activateCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categora con id: " + id + " no encontrada"));

        category.setActive(true);
        Category activated = categoryRepository.save(category);

        return modelMapper.map(activated, CategoryDTO.class);
    }
}
