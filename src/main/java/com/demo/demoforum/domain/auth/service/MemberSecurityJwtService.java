package com.demo.demoforum.domain.auth.service;

import com.demo.demoforum.domain.auth.entity.Authority;
import com.demo.demoforum.domain.member.entity.Member;
import com.demo.demoforum.domain.member.repository.MemberRepository;
import com.demo.demoforum.global.exception.BizException;
import com.demo.demoforum.global.exception.MemberExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberSecurityJwtService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("username = {}", username);
        return memberRepository.findByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new BizException(MemberExceptionType.NOT_FOUND_MEMBER));
    }

    public UserDetails createUserDetails(Member member) {
        String usename = member.getUsername();
        String password = member.getPassword();
        List<SimpleGrantedAuthority> authorities = member.getAuthorities().stream()
                .map(Authority::getAuthorityName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        authorities.forEach(o -> log.debug("authority -> {}", o.getAuthority()));

        return new User(usename, password, authorities);
    }

    public Member getUser(String username) {
        return memberRepository.findByUsername(username).orElseThrow((() -> new BizException(MemberExceptionType.NOT_FOUND_MEMBER)));
    }
}
