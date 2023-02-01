package com.example.fishingstuffshopbackend.controller;

import com.example.fishingstuffshopbackend.dto.NewProductDto;
import com.example.fishingstuffshopbackend.mapper.ProductMapper;
import com.example.fishingstuffshopbackend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    public ProductController(ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok()
                .body(
                        service.findAll()
                                .stream().map(mapper::toDto)
                                .collect(Collectors.toList())
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        return ResponseEntity.ok()
                .body(
                        mapper.toDto(service.findById(id))
                );
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewProductDto productDto) {
        return ResponseEntity.ok()
                .body(
                        mapper.toDto(service.create(mapper.toEntity(productDto)))
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody NewProductDto productDto) {
        return ResponseEntity.ok()
                .body(
                        mapper.toDto(service.update(id, mapper.toEntity(productDto)))
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{prodId}/category/{catId}")
    public ResponseEntity<?> addToCategory(@PathVariable(name = "prodId") long productId,
                                           @PathVariable(name = "catId") long categoryId) {
        return ResponseEntity.ok()
                .body(
                        mapper.toDto(service.addToCategory(productId, categoryId))
                );
    }

    @DeleteMapping("/{prodId}/category/{catId}")
    public ResponseEntity<?> removeFromCategory(@PathVariable(name = "prodId") long productId,
                                           @PathVariable(name = "catId") long categoryId) {
        return ResponseEntity.ok()
                .body(
                        mapper.toDto(service.removeFromCategory(productId, categoryId))
                );
    }
}
