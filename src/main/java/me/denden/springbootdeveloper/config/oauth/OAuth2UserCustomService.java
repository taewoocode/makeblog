package me.denden.springbootdeveloper.config.oauth;

import lombok.RequiredArgsConstructor;
import me.denden.springbootdeveloper.domain.User;
import me.denden.springbootdeveloper.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws
            OAuth2AuthenticationException {
        OAuth2User user = super.loadUser( userRequest );
        return user;
    }

    //유저가 있으면 유저를 업데이트 해주고, 없으면 없다고 만든다.
    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get( "email" );
        String name = (String) attributes.get( "name" );
        User user = userRepository.findByEmail( email )
                .map( entity -> entity.update( name ) )
                .orElse( User.builder()
                        .email( email )
                        .nickName( name )
                        .build() );
        return userRepository.save( user );
    }

}
