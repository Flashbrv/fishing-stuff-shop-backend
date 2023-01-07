package com.example.fishingstuffshopbackend.controller;

import com.example.fishingstuffshopbackend.model.Product;
import com.example.fishingstuffshopbackend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok()
                .body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        return ResponseEntity.ok()
                .body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
        return ResponseEntity.ok()
                .body(service.create(product));
    }

    @PostMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<?> addToCategory(@PathVariable long productId,
                                           @PathVariable long categoryId) {
        return ResponseEntity.accepted()
                .body(service.addProductToCategory(productId, categoryId));
    }

    @GetMapping("/{productId}/category")
    public ResponseEntity<?> getProductCategories(@PathVariable long productId) {
        return ResponseEntity.accepted()
                .body(service.getProductCategories(productId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Product product) {
        return ResponseEntity.ok()
                .body(service.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
