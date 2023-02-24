package com.example.fishingstuffshopbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Set<RoleDTO> roles;
}
