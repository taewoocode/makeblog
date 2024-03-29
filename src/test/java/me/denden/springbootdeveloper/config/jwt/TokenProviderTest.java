package me.denden.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Jwts;
import me.denden.springbootdeveloper.domain.User;
import me.denden.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


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
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("만료 토큰 검증 실패하게끔 설정")
    @Test
    void validToken_invalidToken() {
        String token = JwtFactory.builder()
                .expiration( new Date( new Date().getTime() - Duration.ofDays( 7 ).toMillis() ) )
                .build().createToken( jwtProperties );
        boolean result = tokenProvider.validToken( token );
        assertThat( result ).isTrue();

    }
    @DisplayName("getAuthentication(): 토큰 기반으로 인증정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    @DisplayName("토큰으로 유저아이디 가져오기")
    @Test
    void getUserId() {

        //given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims( Map.of( "id", "userId" ) )
                .build()
                .createToken( jwtProperties );


        //when
        Long userIdByToken = tokenProvider.getUserId( token );

        //then
        assertThat( userIdByToken ).isEqualTo( userId );
    }
}
