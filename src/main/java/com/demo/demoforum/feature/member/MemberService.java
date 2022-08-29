package com.demo.demoforum.feature.member;

import com.demo.demoforum.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(String email, String password, String username) {
        memberRepository.save(
                Member.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .username(username)
                        .build()
        );
    }

    public Member searchMember(String username) {
        return memberRepository.findByUsername(username).
                orElseThrow(() -> new DataNotFoundException("member not found"));
    }
}
