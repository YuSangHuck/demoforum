package com.demo.demoforum.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(String email, String password, String username) {
        userRepository.save(
                User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .username(username)
                        .build()
        );
    }
}
