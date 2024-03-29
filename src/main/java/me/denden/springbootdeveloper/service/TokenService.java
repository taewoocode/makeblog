package me.denden.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.denden.springbootdeveloper.config.jwt.TokenProvider;
import me.denden.springbootdeveloper.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    //토큰 유효성 검사
    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken( refreshToken )){
            throw new IllegalStateException( "Unexpected token" );
        }

        Long userId = refreshTokenService.findByRefreshToken( refreshToken ).getUserId();
        User user = userService.findById( userId );
        return tokenProvider.generateToken( user, Duration.ofDays( 2 ) );

    }
}
