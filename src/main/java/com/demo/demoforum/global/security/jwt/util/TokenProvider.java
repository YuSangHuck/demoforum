package com.demo.demoforum.global.security.jwt.util;

import com.demo.demoforum.domain.auth.entity.Authority;
import com.demo.demoforum.global.security.jwt.authentication.CustomEmailPasswordAuthToken;
import com.demo.demoforum.global.security.jwt.dto.TokenRespDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Component
public class TokenProvider {
    public static final String AUTHORITIES_KEY = "auth";
    public static final String BEARER_TYPE = "Bearer ";

    private final long ACCESS_TOKEN_EXPIRE_TIME;
    private final long REFRESH_TOKEN_EXPIRE_TIME;

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.access-token-expire-time}") long accessTime,
                         @Value("${jwt.refresh-token-expire-time}") long refreshTime
    ) {
        this.ACCESS_TOKEN_EXPIRE_TIME = accessTime;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshTime;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private String createToken(String username, Set<Authority> authorities, long tokenValid) {
        Claims claims = Jwts.claims().setSubject(username);

        claims.put(AUTHORITIES_KEY,
                authorities.stream()
                        .map(Authority::getAuthorityName)
                        .collect(Collectors.joining(".")));

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValid))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessToken(String username, Set<Authority> authorities) {
        return createToken(username, authorities, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String createRefreshToken(String username, Set<Authority> authorities) {
        return createToken(username, authorities, REFRESH_TOKEN_EXPIRE_TIME);
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public TokenRespDTO createTokenDTO(String accessToken, String refreshToken) {
        return TokenRespDTO.builder()
                .accessToken(accessToken)
                .accessTokenTime((int) ACCESS_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken)
                .refreshTokenTime((int) REFRESH_TOKEN_EXPIRE_TIME)
                .grantType(BEARER_TYPE)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        log.debug("claims.getAuth = {}", claims.get(AUTHORITIES_KEY));
        log.debug("claims.getUsername = {}", claims.getSubject());

        List<SimpleGrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        authorities.forEach(
                simpleGrantedAuthority -> log.debug("authorities = {}", simpleGrantedAuthority.getAuthority())
        );

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new CustomEmailPasswordAuthToken(principal, "", authorities);
    }

    /**
     * token을 validate 한 결과를 반환
     *
     * @param token String
     * @return 0: valid, 1: expired, 2: invalid
     */
    public int validateToken(String token) {
        try {
            tokenToJws(token);
            return 0;
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰입니다");
            return 1;
        } catch (Exception e) {
            log.info("잘못된 토큰입니다");
            return -1;
        }
    }

    private Claims parseClaims(String token) {
        try {
            return tokenToJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private Jws<Claims> tokenToJws(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
