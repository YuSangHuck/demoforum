package com.demo.demoforum.domain.member.service;

import com.demo.demoforum.domain.auth.entity.Authority;
import com.demo.demoforum.domain.member.entity.Member;
import com.demo.demoforum.domain.member.exception.MemberExceptionType;
import com.demo.demoforum.domain.member.repository.MemberRepository;
import com.demo.demoforum.global.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(String email, String password, String username, Set<Authority> authorities) {
        memberRepository.save(
                Member.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .username(username)
                        .authorities(authorities)
                        .build()
        );
    }

    public Member searchMember(String username) {
        return memberRepository.findByUsername(username).
                orElseThrow(() -> new BizException(MemberExceptionType.NOT_FOUND_MEMBER));
    }
}
