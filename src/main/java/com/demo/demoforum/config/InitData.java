package com.demo.demoforum.config;

import com.demo.demoforum.feature.authority.Authority;
import com.demo.demoforum.feature.authority.AuthorityRepository;
import com.demo.demoforum.feature.authority.UserAuth;
import com.demo.demoforum.feature.member.Member;
import com.demo.demoforum.feature.member.MemberRepository;
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
        authorities.add(new Authority(UserAuth.ROLE_USER));
        Member member = Member.builder()
                .password(passwordEncoder.encode("7vktD@L62@T@XS"))
                .username("demo")
                .email("demo@forum.com")
                .authorities(authorities)
                .build();

        if (memberRepository.findByUsername(member.getUsername()).isEmpty()) {
            memberRepository.save(member);
        }

        authorities.add(new Authority(UserAuth.ROLE_ADMIN));
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
        for (UserAuth value : UserAuth.values()) {
            Authority authority = Authority.builder()
                    .authorityName(value)
                    .build();
            authorityRepository.save(authority);
        }
    }
}
