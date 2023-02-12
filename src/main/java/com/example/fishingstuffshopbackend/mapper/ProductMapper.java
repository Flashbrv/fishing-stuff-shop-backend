package com.example.fishingstuffshopbackend.mapper;

import com.example.fishingstuffshopbackend.dto.NewProductDto;
import com.example.fishingstuffshopbackend.dto.ProductDto;
import com.example.fishingstuffshopbackend.domain.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    public abstract ProductDto toDto(Product product);
    public abstract Product toEntity(NewProductDto dto);
}
