package com.example.fishingstuffshopbackend.controller;

import com.example.fishingstuffshopbackend.domain.Role;
import com.example.fishingstuffshopbackend.domain.User;
import com.example.fishingstuffshopbackend.dto.RegistrationRequest;
import com.example.fishingstuffshopbackend.dto.RoleToUserForm;
import com.example.fishingstuffshopbackend.dto.UserDto;
import com.example.fishingstuffshopbackend.mapper.RoleMapper;
import com.example.fishingstuffshopbackend.mapper.UserMapper;
import com.example.fishingstuffshopbackend.service.UserService;
import com.example.fishingstuffshopbackend.validator.RegistrationRequestValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final RegistrationRequestValidatorService validatorService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok()
                .body(
                        service.findAll()
                                .stream().map(userMapper::toDto)
                                .collect(Collectors.toList())
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        return ResponseEntity.ok()
                .body(
                        userMapper.toDto(service.findById(id))
                );
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RegistrationRequest registrationRequest) {
        validatorService.test(registrationRequest);
        User newUser = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .password(registrationRequest.getPassword())
                .build();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user").toUriString());
        return ResponseEntity.created(uri)
                .body(
                        userMapper.toDto(service.create(newUser))
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok()
                .body(
                        userMapper.toDto(service.update(id, userMapper.toEntity(userDto)))
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create/role")
    public ResponseEntity<?> createRole(@RequestParam String name) {
        Role role = new Role(name);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/role").toUriString());
        return ResponseEntity.created(uri)
                .body(
                        roleMapper.toDto(service.save(role))
                );
    }

    @PostMapping("/add/role")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        service.addRoleToUser(form.getEmail(), form.getRoleName());
        return ResponseEntity.ok()
                .build();
    }
}
