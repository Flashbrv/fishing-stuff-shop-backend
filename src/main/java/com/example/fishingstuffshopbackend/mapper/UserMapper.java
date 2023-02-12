package com.example.fishingstuffshopbackend.mapper;

import com.example.fishingstuffshopbackend.dto.UserDto;
import com.example.fishingstuffshopbackend.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract UserDto toDto(User user);
    public abstract User toEntity(UserDto user);
}
