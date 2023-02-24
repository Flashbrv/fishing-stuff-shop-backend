package com.example.fishingstuffshopbackend.controller;

import com.example.fishingstuffshopbackend.security.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/token/refresh")
@Slf4j
@RequiredArgsConstructor
public class TokenController {
    private final JwtTokenManager tokenManager;

    @GetMapping
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        tokenManager.updateAccessToken(request, response);
    }
}
