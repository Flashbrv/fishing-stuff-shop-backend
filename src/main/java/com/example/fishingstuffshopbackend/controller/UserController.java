package com.example.fishingstuffshopbackend.controller;

import com.example.fishingstuffshopbackend.dto.UserDto;
import com.example.fishingstuffshopbackend.mapper.UserMapper;
import com.example.fishingstuffshopbackend.domain.Role;
import com.example.fishingstuffshopbackend.domain.User;
import com.example.fishingstuffshopbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok()
                .body(
                        service.findAll()
                                .stream().map(mapper::toDto)
                                .collect(Collectors.toList())
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        return ResponseEntity.ok()
                .body(
                        mapper.toDto(service.findById(id))
                );
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam String firstName,
                                    @RequestParam String lastName,
                                    @RequestParam String email,
                                    @RequestParam String phoneNumber,
                                    @RequestParam String password) {
        User newUser = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .roles(new ArrayList<Role>() {{add(new Role("ROLE_USER"));}})
                .build();
        return ResponseEntity.ok()
                .body(
                        mapper.toDto(service.create(newUser))
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok()
                .body(
                        mapper.toDto(service.update(id, mapper.toEntity(userDto)))
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
