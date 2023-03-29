package com.example.fishingstuffshopbackend.filter;

import com.example.fishingstuffshopbackend.security.JwtTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenManager tokenManager;

    public CustomAuthorizationFilter(JwtTokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isRequestToPublicPath(request)) {
            filterChain.doFilter(request, response);
        } else {
            if (!tokenManager.validateAccessToken(request, response)) {
                log.info("Unauthenticated request to {}", request.getRequestURL());
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    private boolean isRequestToPublicPath(HttpServletRequest request) {
        if (request.getServletPath().equals("/api/v1/login") ||
                request.getServletPath().equals("/api/v1/token/refresh") ||
                request.getServletPath().contains("/swagger-ui/") ||
                request.getServletPath().contains("/v3/api-docs")) {
            return true;
        }
        return false;
    }
}
