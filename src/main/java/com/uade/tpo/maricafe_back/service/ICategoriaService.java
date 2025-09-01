package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.dto.CategoryDTO;
import com.uade.tpo.maricafe_back.entity.dto.CategoryDTO;
import com.uade.tpo.maricafe_back.entity.dto.CreateCategoriaDTO;
import com.uade.tpo.maricafe_back.exceptions.CategoryDuplicateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    Page<CategoryDTO> getCategories(Pageable pageable);

    Optional<CategoryDTO> getCategoryById(Integer id);

    CategoryDTO createCategory(CreateCategoriaDTO dto);

    void deleteCategoryById(Integer id);

    CategoryDTO updateCategory(Integer id, CreateCategoriaDTO dto);
}
