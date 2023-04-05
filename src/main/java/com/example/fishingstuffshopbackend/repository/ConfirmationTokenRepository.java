package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.domain.ConfirmationToken;
import com.example.fishingstuffshopbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);

    @Query("SELECT t FROM ConfirmationToken t WHERE t.user=?1")
    List<ConfirmationToken> findTokensByUserId(User user);
}
