package me.denden.springbootdeveloper.config.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.denden.springbootdeveloper.util.CookieUtil;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;

public class OAuth2AuthorizationRequestBasedOnCookieRepository implements
        AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oatuh2_auth_reponse";
    private final static int COOKIE_EXPIRE_SECONDS = 18000;


    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie( request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME );
        return CookieUtil.deserialize( cookie, OAuth2AuthorizationRequest.class );
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest( request );
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            removeAuthorizationRequest( request, response );
            return;
        }
        CookieUtil.addCookie( response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtil.serialize(
                authorizationRequest ), COOKIE_EXPIRE_SECONDS );
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request,
                                                  HttpServletResponse response) {
        CookieUtil.deleteCookie( request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME );
    }
}
