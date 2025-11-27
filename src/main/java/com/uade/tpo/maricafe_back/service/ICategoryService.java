package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.dto.CategoryDTO;
import com.uade.tpo.maricafe_back.entity.dto.CreateCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICategoryService {
    Page<CategoryDTO> getCategories(Pageable pageable);

    Optional<CategoryDTO> getCategoryById(Integer id);

    CategoryDTO createCategory(CreateCategoryDTO dto);

    void deleteCategoryById(Integer id);

    CategoryDTO updateCategory(Integer id, CreateCategoryDTO dto);

    CategoryDTO activateCategory(Integer id);
}
