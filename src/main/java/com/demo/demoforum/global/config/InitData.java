package com.demo.demoforum.global.config;

import com.demo.demoforum.domain.auth.entity.AuthRole2;
import com.demo.demoforum.domain.auth.entity.Authority;
import com.demo.demoforum.domain.auth.repository.AuthorityRepository;
import com.demo.demoforum.domain.member.entity.Member;
import com.demo.demoforum.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitData {
    private final AuthorityRepository authorityRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void initData() {
        initUserAuths();
        initMembers();
    }

    private void initMembers() {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority(AuthRole2.ROLE_USER));
        Member member = Member.builder()
                .password(passwordEncoder.encode("7vktD@L62@T@XS"))
                .username("demo")
                .email("demo@forum.com")
                .authorities(authorities)
                .build();

        if (memberRepository.findByUsername(member.getUsername()).isEmpty()) {
            memberRepository.save(member);
        }

        authorities.add(new Authority(AuthRole2.ROLE_ADMIN));
        Member admin = Member.builder()
                .password(passwordEncoder.encode("h2q!@7GaRf9tJH"))
                .username("admin")
                .email("admin@forum.com")
                .authorities(authorities)
                .build();

        if (memberRepository.findByUsername(admin.getUsername()).isEmpty()) {
            memberRepository.save(admin);
        }
    }

    private void initUserAuths() {
        for (AuthRole2 value : AuthRole2.values()) {
            Authority authority = Authority.builder()
                    .authorityName(value)
                    .build();
            authorityRepository.save(authority);
        }
    }
}
