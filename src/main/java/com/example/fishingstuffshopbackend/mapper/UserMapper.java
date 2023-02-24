package com.example.fishingstuffshopbackend.mapper;

import com.example.fishingstuffshopbackend.domain.User;
import com.example.fishingstuffshopbackend.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public abstract class UserMapper {

    public abstract UserDto toDto(User user);
    public abstract User toEntity(UserDto dto);
}
