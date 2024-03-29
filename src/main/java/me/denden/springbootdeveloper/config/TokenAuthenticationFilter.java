package me.denden.springbootdeveloper.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.denden.springbootdeveloper.config.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterchain) throws ServletException, IOException {
        //키값조회
        String authorizationHeader = request.getHeader( HEADER_AUTHORIZATION );
        //가져온 값에서 접두사 제거
        String token = getAccessToken( authorizationHeader );
        //토큰조회, 토큰이 유효한지 확인, 유효하다면 인증정보를 설정
        if(tokenProvider.validToken( token )) {
            Authentication authentication = tokenProvider.getAuthentication( token );
            SecurityContextHolder.getContext().setAuthentication( authentication );
        }

        filterchain.doFilter( request, response );
    }

    private String getAccessToken(String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith( TOKEN_PREFIX )){
            return authorizationHeader.substring( TOKEN_PREFIX.length() );
        }
        return null;
    }
}
