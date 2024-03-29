package me.denden.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.denden.springbootdeveloper.domain.User;
import me.denden.springbootdeveloper.dto.AddUserRequest;
import me.denden.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return userRepository.save( User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode( dto.getPassword() ))
                .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById( userId )
                .orElseThrow( () -> new IllegalArgumentException( "Unexpected user" ) );
    }

}
