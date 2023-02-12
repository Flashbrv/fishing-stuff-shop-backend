package com.example.fishingstuffshopbackend.mapper;

import com.example.fishingstuffshopbackend.dto.CategoryDto;
import com.example.fishingstuffshopbackend.dto.NewCategoryDto;
import com.example.fishingstuffshopbackend.domain.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {
    public abstract CategoryDto toDto(Category entity);
    public abstract Category toEntity(NewCategoryDto dto);
}
