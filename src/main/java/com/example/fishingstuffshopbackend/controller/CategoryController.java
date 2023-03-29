package com.example.fishingstuffshopbackend.controller;

import com.example.fishingstuffshopbackend.domain.Category;
import com.example.fishingstuffshopbackend.dto.CategoryDto;
import com.example.fishingstuffshopbackend.mapper.CategoryMapper;
import com.example.fishingstuffshopbackend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    public CategoryController(CategoryService categoryService, CategoryMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok()
                .body(categoryService
                        .findAll()
                        .stream().map(mapper::toDto)
                        .collect(Collectors.toList())
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable long id) {
        return ResponseEntity.ok()
                .body(mapper.toDto(categoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody final CategoryDto categoryDto) {
        Category newCategory = categoryService.create(mapper.toEntity(categoryDto));
        URI uri = URI.create(
                ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/category")
                    .toUriString()
        );
        return ResponseEntity
                .created(uri)
                .body(mapper.toDto(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable long id, @RequestBody final CategoryDto categoryDto) {
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
