package me.denden.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Jwts;
import me.denden.springbootdeveloper.domain.User;
import me.denden.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;


    @DisplayName("generateToken 유저 정보, 만료기간 전달해서 토큰 만들기")
    @Test
    void generateToken() {
        User testUser = userRepository.save( User.builder()
                .email( "user@gmail.com" )
                .password( "test" )
                .build());

        String token = tokenProvider.generateToken( testUser, Duration.ofDays(14) );
        Long userId = Jwts.parser()
                .setSigningKey( jwtProperties.getSecretKey() )
                .parseClaimsJws( token )
                .getBody()
                .get( "id", Long.class );

        assertThat( userId ).isEqualTo( testUser.getId() );
    }

}
