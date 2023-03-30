package com.example.fishingstuffshopbackend.controller;

import com.example.fishingstuffshopbackend.dto.RegistrationRequest;
import com.example.fishingstuffshopbackend.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;


    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam String token) {
        return registrationService.confirmEmail(token);
    }
}
