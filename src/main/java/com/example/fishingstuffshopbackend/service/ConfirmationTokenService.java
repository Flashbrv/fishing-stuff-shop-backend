package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.domain.ConfirmationToken;
import com.example.fishingstuffshopbackend.domain.User;
import com.example.fishingstuffshopbackend.repository.ConfirmationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ConfirmationTokenService {
    @Value("${confirmation.token.expiration.time}")
    private long EXPIRATION_TIME_IN_MINUTE;
    private final ConfirmationTokenRepository repository;

    public ConfirmationTokenService(ConfirmationTokenRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public String createTokenFor(User user) {
        String token = UUID.randomUUID().toString();
        log.info("Create token:{} for user: {}", token, user.getEmail());
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusMinutes(EXPIRATION_TIME_IN_MINUTE);

        ConfirmationToken confirmationToken = new ConfirmationToken(token, createdAt, expiresAt, user);

        log.info("Save confirmation token");
        repository.save(confirmationToken);

        return token;
    }

    @Transactional
    public Optional<ConfirmationToken> confirmToken(String token) {
        log.info("Validate token: {}", token);
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }

        Optional<ConfirmationToken> tokenOptional = repository.findByToken(token);
        if (!tokenOptional.isPresent()) {
            log.info("There is no token:{} in database", token);
            return Optional.empty();
        }

        ConfirmationToken confirmationToken = tokenOptional.get();
        if (confirmationToken.getConfirmedAt() != null) {
            log.info("Token: {} already confirmed at {}", token, confirmationToken.getConfirmedAt());
            return Optional.empty();
        }
        if (!isNotExpired(confirmationToken)) {
            log.info("Token: {} expired at {}", token, confirmationToken.getExpiresAt());
            return Optional.empty();
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());

        return Optional.of(confirmationToken);
    }

    private boolean isNotExpired(ConfirmationToken token) {
        return token.getExpiresAt().isAfter(LocalDateTime.now());
    }
}
