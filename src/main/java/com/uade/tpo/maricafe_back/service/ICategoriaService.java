package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.dto.CategoriaDTO;
import com.uade.tpo.maricafe_back.entity.dto.CreateCategoriaDTO;
import com.uade.tpo.maricafe_back.exceptions.CategoryDuplicateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    Page<CategoriaDTO> getCategories(Pageable pageable);

    Optional<CategoriaDTO> getCategoryById(Integer id);

    CategoriaDTO createCategory(CreateCategoriaDTO dto);

    void deleteCategoryById(Integer id);

    CategoriaDTO updateCategory(Integer id, CreateCategoriaDTO dto);
}
