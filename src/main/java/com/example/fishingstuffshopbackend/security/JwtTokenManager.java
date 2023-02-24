package com.example.fishingstuffshopbackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.fishingstuffshopbackend.domain.User;
import com.example.fishingstuffshopbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Slf4j
public class JwtTokenManager {
    private static final String TOKEN_PREFIX = "Bearer ";
    private Algorithm algorithm;
    private JWTVerifier verifier;

    @Value("${jwt.secret.key}")
    private String secret_key;

    @Value("${jwt.access.token.expiration_in_min}")
    private long accessTokenExpirationTime;

    @Value("${jwt.refresh.token.expiration_in_min}")
    private long refreshTokenExpirationTime;

    private final UserService service;

    public JwtTokenManager(UserService service) {
        this.service = service;
    }

    @PostConstruct
    private void init() {
        algorithm = Algorithm.HMAC256(secret_key.getBytes());
        verifier = JWT.require(algorithm).build();
    }

    private String generateAccessToken(String email, List<String> roles, long expTimeInMinutes, String url) {
        log.info("Generate access token for {}, expTime: {} minutes, url: {}", email, expTimeInMinutes, url);
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expTimeInMinutes * 60 * 1000))
                .withIssuer(url)
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    private String generateRefreshToken(String email, long expTimeInMinutes, String url) {
        log.info("Generate refresh token for {}, expTime: {} minutes, url: {}", email, expTimeInMinutes, url);
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expTimeInMinutes * 60 * 1000))
                .withIssuer(url)
                .sign(algorithm);
    }

    public void generateTokens(org.springframework.security.core.userdetails.User user,
                               HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String url = request.getRequestURL().toString();
        String accessToken = generateAccessToken(user.getUsername(), roles, accessTokenExpirationTime, url);
        String refreshToken = generateRefreshToken(user.getUsername(), refreshTokenExpirationTime, url);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        writeToResponse(response, tokens);
    }

    public void updateAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (validateHeader(authorizationHeader)) {
            try {
                String refreshToken = authorizationHeader.substring(TOKEN_PREFIX.length());
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String email = decodedJWT.getSubject();

                User user = service.getUser(email);
                List<String> roles = user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
                String accessToken = generateAccessToken(user.getEmail(), roles, accessTokenExpirationTime, request.getRequestURL().toString());

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);

                writeToResponse(response, tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getLocalizedMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> errors = new HashMap<>();
                errors.put("error_message", exception.getLocalizedMessage());

                writeToResponse(response, errors);
            }
        } else {
            log.info("Refresh token is missing");
            throw new RuntimeException("Refresh token is missing");
        }
    }

    public boolean validateAccessToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!validateHeader(authorizationHeader)) {
            log.error("Authorization header is absent");
            response.setHeader("error", "Authorization header is absent");
            response.setStatus(UNAUTHORIZED.value());
            return false;
        }

        try {
            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            DecodedJWT decodedJWT = verifier.verify(token);
            String email = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
            return true;
        } catch (Exception exception) {
            log.error("Error login in: {}", exception.getLocalizedMessage());
            response.setHeader("error", exception.getLocalizedMessage());
            response.setStatus(UNAUTHORIZED.value());

            Map<String, String> errors = new HashMap<>();
            errors.put("error_message", exception.getLocalizedMessage());

            writeToResponse(response, errors);
        }

        return false;
    }

    private static void writeToResponse(HttpServletResponse response, Map<String, String> data) throws IOException {
        log.info("Writing data to response");
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), data);
    }

    private boolean validateHeader(String header) {
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            return true;
        }
        return false;
    }
}
