package com.example.fishingstuffshopbackend.controller;

import com.example.fishingstuffshopbackend.exception.ParameterMissingOrBlankException;
import com.example.fishingstuffshopbackend.service.ProductTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product_type")
public class ProductTypeController {

    private final ProductTypeService typeService;

    public ProductTypeController(ProductTypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok()
                .body(typeService.findAll());
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<?> getTypeById(@PathVariable Long typeId) {
        return ResponseEntity.ok()
                .body(typeService.findById(typeId));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam(required = false) String typeName) {
        if (typeName == null || typeName.isBlank()) {
            throw new ParameterMissingOrBlankException("typeName");
        }

        return ResponseEntity.ok()
                .body(typeService.create(typeName));
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<?> update(@PathVariable Long typeId, @RequestParam String typeName) {
        return ResponseEntity.ok()
                .body(typeService.update(typeId, typeName));
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<?> delete(@PathVariable Long typeId) {
        typeService.delete(typeId);
        return ResponseEntity.noContent().build();
    }
}
