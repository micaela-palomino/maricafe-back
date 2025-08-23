package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.Category;
import com.uade.tpo.maricafe_back.entity.dto.CategoryRequest;
import com.uade.tpo.maricafe_back.exceptions.CategoryDuplicateException;
import com.uade.tpo.maricafe_back.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("categories")
public class CategoriesController {
    private CategoryService categoryService;

    public CategoriesController() {
        categoryService = new CategoryService();
    }

    @GetMapping
    public ResponseEntity<ArrayList<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int categoryId) {
        Optional<Category> result = categoryService.getCategoryById(categoryId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequest categoryRequest)
            throws CategoryDuplicateException {
        Category result = categoryService.createCategory(categoryRequest.getId(), categoryRequest.getDescription());
        return ResponseEntity.created(URI.create("/categories/" + result.getId())).body(result);
    }
}
