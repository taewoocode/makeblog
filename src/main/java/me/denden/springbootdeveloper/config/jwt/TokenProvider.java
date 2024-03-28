package me.denden.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import me.denden.springbootdeveloper.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken( new Date( now.getTime() + expiredAt.toMillis() ), user );
    }

    private String makeToken(Date expiry, User user) {
        Date now = new Date();
        return Jwts.builder()
                .setIssuer( jwtProperties.getIssuer() )
                .setIssuedAt( now )
                .setExpiration( expiry )
                .setSubject( user.getEmail() )
                .claim( "id", user.getId() )
                .signWith( SignatureAlgorithm.ES256, jwtProperties.getSecretKey() )
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey( jwtProperties.getSecretKey() )
                    .parseClaimsJws( token );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //토큰 기반 인증 정보 가져오기
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims( token );
        Set<SimpleGrantedAuthority> authorities = Collections.singleton( new
                SimpleGrantedAuthority( "Role_USER" ) );

        return new UsernamePasswordAuthenticationToken( new org.springframework.security.core.
                userdetails.User( claims.getSubject(), "", authorities )
                , token, authorities );
    }


    //토큰 기반 user ID를 가져옴
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey( jwtProperties.getSecretKey() )
                .parseClaimsJws( token )
                .getBody();
    }


    public Long getUserId(String token) {
        Claims claims = getClaims( token );
        return claims.get( "id", Long.class );
    }
}
