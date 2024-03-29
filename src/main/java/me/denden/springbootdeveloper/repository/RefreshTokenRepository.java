package me.denden.springbootdeveloper.repository;

import me.denden.springbootdeveloper.domain.ReFreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<ReFreshToken, Long> {
    Optional<ReFreshToken> findByUserId(Long userId);
    Optional<ReFreshToken> findByRefreshToken(String refreshToken);

}
