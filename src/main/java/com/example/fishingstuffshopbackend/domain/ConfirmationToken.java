package com.example.fishingstuffshopbackend.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "fs_confirmation_tokens")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public ConfirmationToken() {
    }

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmationToken that = (ConfirmationToken) o;
        return id.equals(that.id)
                && token.equals(that.token)
                && createdAt.equals(that.createdAt)
                && expiresAt.equals(that.expiresAt)
                && Objects.equals(confirmedAt, that.confirmedAt)
                && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, createdAt, expiresAt, confirmedAt, user);
    }

    @Override
    public String toString() {
        return "ConfirmationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", confirmedAt=" + confirmedAt +
                ", user=" + user +
                '}';
    }
}
