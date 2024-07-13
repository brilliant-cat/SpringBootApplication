package com.example.springexample.jpa.controllers;

import com.example.springexample.jpa.dto.CategoryDto;
import com.example.springexample.jpa.services.CategoriesCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoriesCRUDService categoriesCRUDService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable long id) {
        try {
            CategoryDto categoryDto = categoriesCRUDService.getById(id);
            return ResponseEntity.ok(categoryDto);
        } catch (NoSuchElementException e) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message:", "Category not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    @GetMapping
    public ConcurrentHashMap<Long, CategoryDto> getAllCategory() {
        return categoriesCRUDService.getAll();
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        long nextId = (categoriesCRUDService.getAll().isEmpty() ? 0 : categoriesCRUDService.getAll().size()) + 1L;
        categoryDto.setId(nextId);
        categoriesCRUDService.create(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id, @RequestBody CategoryDto categoryDto) {
        if (!categoriesCRUDService.getAll().containsKey(id)) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message:", "Category not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
        categoryDto.setId(id);
        categoriesCRUDService.update(categoryDto);
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {
        if (!categoriesCRUDService.getAll().containsKey(id)) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message:", "Category not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
        categoriesCRUDService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
