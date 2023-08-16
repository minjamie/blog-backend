package com.example.blog.repository.refreshToken;

import com.example.blog.domain.RefreshToken;
import com.example.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByValue(String value);
    Optional<RefreshToken> findByUser(User user);

    void deleteByValue(String value);
}
