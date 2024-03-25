package me.denden.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.denden.springbootdeveloper.domain.User;
import me.denden.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository; // 의존성 추가

    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail( email )
                .orElseThrow( () -> new IllegalArgumentException( (email) ) );


    }
}
