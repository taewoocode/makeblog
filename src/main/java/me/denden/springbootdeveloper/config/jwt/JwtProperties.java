package me.denden.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class JwtProperties {
    private String issuer;
    private String secretKey;
}
