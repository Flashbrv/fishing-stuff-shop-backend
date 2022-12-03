package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ProductInMemoryDB {
    INSTANCE;
    private static List<ProductDto> list = new ArrayList<ProductDto>();
    private static Integer lastId = 0;

    public Integer getId() { return ++lastId; }

    public void add(ProductDto productDto) {
        productDto.setId(getId());
        list.add(productDto);
    }

    public List<ProductDto> findAll() { return list; }

    public Optional<ProductDto> findById(Integer id) {
        return list.stream()
                .filter(p -> p.getId() == id)
                .findAny();
    }

    public void remove(Integer id) {
        ProductDto toRemove = null;
        for (ProductDto dto:list)
            if (dto.getId()==id) toRemove = dto;
        if (toRemove!=null) list.remove(toRemove);
    }

    public void edit(ProductDto productDto) {
        for (ProductDto dto:list)
            if (dto.getId()==productDto.getId())
                dto.setName(productDto.getName());
    }
}
