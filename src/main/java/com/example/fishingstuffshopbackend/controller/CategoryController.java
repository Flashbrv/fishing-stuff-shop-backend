package com.example.fishingstuffshopbackend.controller;

import com.example.fishingstuffshopbackend.dto.NewCategoryDto;
import com.example.fishingstuffshopbackend.mapper.CategoryMapper;
import com.example.fishingstuffshopbackend.domain.Category;
import com.example.fishingstuffshopbackend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    public CategoryController(CategoryService categoryService, CategoryMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok()
                .body(categoryService
                        .findAll()
                        .stream().map(mapper::toDto)
                        .collect(Collectors.toList())
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        return ResponseEntity.ok()
                .body(mapper.toDto(categoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewCategoryDto categoryDto) {
        Category newCategory = categoryService.create(mapper.toEntity(categoryDto));
        return ResponseEntity.ok()
                .body(mapper.toDto(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody NewCategoryDto categoryDto) {
        Category updatedCategory = categoryService.update(id, mapper.toEntity(categoryDto));
        return ResponseEntity.ok()
                .body(mapper.toDto(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
