package com.demo.demoforum.feature.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    //    NOTE UserDetailsService, GrantedAuthority, SimpleGrantedAuthority
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("start");
        SiteUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        log.info("return");
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
