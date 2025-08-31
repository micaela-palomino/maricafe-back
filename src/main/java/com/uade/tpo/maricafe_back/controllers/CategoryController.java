package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.Categoria;
import com.uade.tpo.maricafe_back.entity.dto.CategoryDTO;
import com.uade.tpo.maricafe_back.entity.dto.CreateCategoriaDTO;
import com.uade.tpo.maricafe_back.exceptions.CategoryDuplicateException;
import com.uade.tpo.maricafe_back.service.ICategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("categories")
public class CategoryController {
    private final ICategoriaService categoriaService;

    public CategoryController(ICategoriaService categoriaService) {
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

    @GetMapping("/{catergoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer catergoryId) {
        Optional<CategoryDTO> category = this.categoriaService.getCategoryById(catergoryId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategoria(@RequestBody CreateCategoriaDTO dto) {
        CategoryDTO saved = categoriaService.createCategory(dto);

        return ResponseEntity
                .created(URI.create("/categories/" + saved.getIdCategoria()))
                .body(saved);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId) {
        categoriaService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Integer categoryId,
            @RequestBody CreateCategoriaDTO dto) {

        CategoryDTO updated = categoriaService.updateCategory(categoryId, dto);
        return ResponseEntity.ok(updated); // 200 OK con la categoría actualizada
    }

}
