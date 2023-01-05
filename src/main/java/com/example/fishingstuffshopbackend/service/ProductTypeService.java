package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.dto.ProductTypeDto;

import java.util.List;

public interface ProductTypeService {
    List<ProductTypeDto> findAll();
    ProductTypeDto findById(Long id);
    ProductTypeDto update(Long id, String newName);
    ProductTypeDto create(String name);
    void delete(Long id);
}
