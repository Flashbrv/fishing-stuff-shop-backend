package com.example.fishingstuffshopbackend.mapper;

import com.example.fishingstuffshopbackend.domain.Role;
import com.example.fishingstuffshopbackend.dto.RoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {

    public abstract RoleDTO toDto(Role role);
    public abstract Role toEntity(RoleDTO dto);
}
