package com.example.fishingstuffshopbackend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.fishingstuffshopbackend.domain.Role;
import com.example.fishingstuffshopbackend.domain.User;
import com.example.fishingstuffshopbackend.dto.RoleToUserForm;
import com.example.fishingstuffshopbackend.dto.UserDto;
import com.example.fishingstuffshopbackend.mapper.RoleMapper;
import com.example.fishingstuffshopbackend.mapper.UserMapper;
import com.example.fishingstuffshopbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    private final UserService service;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public UserController(UserService service, UserMapper userMapper, RoleMapper roleMapper) {
        this.service = service;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

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
