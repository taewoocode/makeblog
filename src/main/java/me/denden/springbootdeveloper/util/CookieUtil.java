package me.denden.springbootdeveloper.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class CookieUtil {
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {

        //쿠기 객체 생성
        Cookie cookie = new Cookie( name, value );

        //쿠키 경로 설정
        cookie.setPath( "/" );
        cookie.setMaxAge( maxAge );
        response.addCookie( cookie );
    }

    //객체를 직렬화하면 객체 상태 그대로 저장한다
    public static String serialize(Object obj) {
        return Base64.getEncoder()
                .encodeToString( SerializationUtils.serialize( obj ) );
    }

    //객체 역직렬화
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode( cookie.getValue()))
        );
    }
}
