package com.demo.demoforum.domain.auth.service;

import com.demo.demoforum.domain.auth.dto.SigninDTO;
import com.demo.demoforum.domain.auth.entity.AuthRole2;
import com.demo.demoforum.domain.auth.entity.Authority;
import com.demo.demoforum.domain.auth.exception.AuthorityExceptionType;
import com.demo.demoforum.domain.auth.repository.AuthorityRepository;
import com.demo.demoforum.domain.jwt.*;
import com.demo.demoforum.domain.jwt.exception.JwtExceptionType;
import com.demo.demoforum.domain.member.dto.MemberFormDto;
import com.demo.demoforum.domain.member.dto.MemberRespDTO;
import com.demo.demoforum.domain.member.entity.Member;
import com.demo.demoforum.domain.member.exception.MemberExceptionType;
import com.demo.demoforum.domain.member.repository.MemberRepository;
import com.demo.demoforum.global.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberSecurityJwtService memberSecurityJwtService;


    @Transactional
    public MemberRespDTO signup(MemberFormDto userFormDto) {
        if (memberRepository.existsByUsername(userFormDto.getEmail())) {
            throw new BizException(MemberExceptionType.DUPLICATE_MEMBER);
        }

        // DB 에서 ROLE_USER를 찾아서 권한으로 추가한다.
        Authority authority = authorityRepository
                .findByAuthorityName(AuthRole2.ROLE_USER)
                .orElseThrow(() -> new BizException(AuthorityExceptionType.NOT_FOUND_AUTHORITY));

        Set<Authority> set = new HashSet<>();
        set.add(authority);


        Member member = userFormDto.toMember(passwordEncoder, set);
        log.debug("member = {}", member);
        return MemberRespDTO.of(memberRepository.save(member));
    }

    @Transactional
    public TokenRespDTO signin(SigninDTO signinDTO) {
        CustomEmailPasswordAuthToken customEmailPasswordAuthToken = new CustomEmailPasswordAuthToken(signinDTO.getUsername(), signinDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(customEmailPasswordAuthToken);
        String username = authenticate.getName();
        Member member = memberSecurityJwtService.getUser(username);

        String accessToken = tokenProvider.createAccessToken(username, member.getAuthorities());
        String refreshToken = tokenProvider.createRefreshToken(username, member.getAuthorities());

        //refresh Token 저장
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .username(username)
                        .value(refreshToken)
                        .build()
        );

        return tokenProvider.createTokenDTO(accessToken, refreshToken);

    }

    @Transactional
    public TokenRespDTO reissue(TokenReqDTO tokenRequestDto) {
        /*
         *  accessToken 은 JWT Filter 에서 검증되고 옴
         * */
        String originAccessToken = tokenRequestDto.getAccessToken();
        String originRefreshToken = tokenRequestDto.getRefreshToken();

        // refreshToken 검증
        int refreshTokenFlag = tokenProvider.validateToken(originRefreshToken);

        log.debug("refreshTokenFlag = {}", refreshTokenFlag);

        //refreshToken 검증하고 상황에 맞는 오류를 내보낸다.
        if (refreshTokenFlag == -1) {
            throw new BizException(JwtExceptionType.BAD_TOKEN); // 잘못된 리프레시 토큰
        } else if (refreshTokenFlag == 1) {
            throw new BizException(JwtExceptionType.REFRESH_TOKEN_EXPIRED); // 유효기간 끝난 토큰
        }

        // 2. Access Token 에서 Member Email 가져오기
        Authentication authentication = tokenProvider.getAuthentication(originAccessToken);

        log.debug("Authentication = {}", authentication);

        // 3. 저장소에서 Member Email 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new BizException(MemberExceptionType.LOGOUT_MEMBER)); // 로그 아웃된 사용자


        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(originRefreshToken)) {
            throw new BizException(JwtExceptionType.BAD_TOKEN); // 토큰이 일치하지 않습니다.
        }

        // 5. 새로운 토큰 생성
        String username = tokenProvider.getUsernameFromToken(originAccessToken);
        Member member = memberSecurityJwtService.getUser(username);

        String newAccessToken = tokenProvider.createAccessToken(username, member.getAuthorities());
        String newRefreshToken = tokenProvider.createRefreshToken(username, member.getAuthorities());
        TokenRespDTO tokenRespDTO = tokenProvider.createTokenDTO(newAccessToken, newRefreshToken);

        log.debug("refresh Origin = {}", originRefreshToken);
        log.debug("refresh New = {} ", newRefreshToken);
        // 6. 저장소 정보 업데이트 (dirtyChecking으로 업데이트)
        refreshToken.updateValue(newRefreshToken);

        // 토큰 발급
        return tokenRespDTO;
    }
}
