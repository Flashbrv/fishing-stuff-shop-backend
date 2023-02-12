package com.example.fishingstuffshopbackend.dto;

import com.example.fishingstuffshopbackend.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
}
