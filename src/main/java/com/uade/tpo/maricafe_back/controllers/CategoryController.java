package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.dto.ApiResponseDTO;
import com.uade.tpo.maricafe_back.entity.dto.CategoryDTO;
import com.uade.tpo.maricafe_back.entity.dto.CreateCategoryDTO;
import com.uade.tpo.maricafe_back.service.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("categories")
public class CategoryController {
    private final ICategoryService categoriaService;

    public CategoryController(ICategoryService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getCategories (
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null || size == null) {
            return ResponseEntity.ok(categoriaService.getCategories(PageRequest.of(0, Integer.MAX_VALUE)));
        }
        return ResponseEntity.ok(categoriaService.getCategories(PageRequest.of(page, size)));
    }


    @PostMapping
    public ResponseEntity<ApiResponseDTO<CategoryDTO>> createCategoria(@RequestBody CreateCategoryDTO dto) {
        CategoryDTO saved = categoriaService.createCategory(dto);

        return ResponseEntity
                .created(URI.create("/categories/" + saved.getCategoryId()))
                .body(ApiResponseDTO.success("Categoría creada con éxito", saved));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteCategory(@PathVariable Integer categoryId) {
        categoriaService.deleteCategoryById(categoryId);
        return ResponseEntity.ok(ApiResponseDTO.success("Categoría eliminada con éxito"));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDTO<CategoryDTO>> updateCategory(
            @PathVariable Integer categoryId,
            @RequestBody CreateCategoryDTO dto) {

        CategoryDTO updated = categoriaService.updateCategory(categoryId, dto);
        return ResponseEntity.ok(ApiResponseDTO.success("Categoría actualizada con éxito", updated));
    }

    @PatchMapping("/{categoryId}/activate")
    public ResponseEntity<ApiResponseDTO<CategoryDTO>> activateCategory(@PathVariable Integer categoryId) {
        CategoryDTO activated = categoriaService.activateCategory(categoryId);
        return ResponseEntity.ok(ApiResponseDTO.success("Categoría activada con éxito", activated));
    }

}
