package com.example.fishingstuffshopbackend.mapper;

import com.example.fishingstuffshopbackend.dto.CategoryDto;
import com.example.fishingstuffshopbackend.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {
    public abstract CategoryDto toDto(Category entity);
    public abstract Category toEntity(CategoryDto dto);
}
