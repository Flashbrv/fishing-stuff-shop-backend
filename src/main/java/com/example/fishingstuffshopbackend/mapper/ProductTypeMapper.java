package com.example.fishingstuffshopbackend.mapper;

import com.example.fishingstuffshopbackend.dto.ProductTypeDto;
import com.example.fishingstuffshopbackend.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductTypeMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "name")
    public abstract ProductTypeDto toDto(Category entity);
}
