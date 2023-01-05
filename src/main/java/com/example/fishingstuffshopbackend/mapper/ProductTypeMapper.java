package com.example.fishingstuffshopbackend.mapper;

import com.example.fishingstuffshopbackend.dto.ProductTypeDto;
import com.example.fishingstuffshopbackend.model.ProductTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductTypeMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    public abstract ProductTypeDto toDto(ProductTypeEntity entity);
}
