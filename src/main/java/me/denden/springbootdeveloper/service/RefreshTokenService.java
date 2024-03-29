package me.denden.springbootdeveloper.service;


import lombok.RequiredArgsConstructor;
import me.denden.springbootdeveloper.domain.ReFreshToken;
import me.denden.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public ReFreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken( refreshToken )
                .orElseThrow( () -> new IllegalArgumentException( "Unexpected token" ) );
    }
}
