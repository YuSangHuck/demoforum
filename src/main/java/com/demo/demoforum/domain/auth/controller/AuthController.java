package com.demo.demoforum.domain.auth.controller;

import com.demo.demoforum.domain.auth.dto.SigninDTO;
import com.demo.demoforum.domain.auth.service.AuthService;
import com.demo.demoforum.domain.jwt.TokenReqDTO;
import com.demo.demoforum.domain.jwt.TokenRespDTO;
import com.demo.demoforum.domain.member.dto.MemberFormDto;
import com.demo.demoforum.domain.member.dto.MemberRespDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public MemberRespDTO signup(@RequestBody MemberFormDto memberFormDto) {
        log.debug("memberRequestDto = {}", memberFormDto);
        return authService.signup(memberFormDto);
    }

    @PostMapping("/signin")
    public TokenRespDTO signin(@RequestBody SigninDTO signinDTO) {
        return authService.signin(signinDTO);
    }

    @PostMapping("/reissue")
    public TokenRespDTO reissue(@RequestBody TokenReqDTO tokenRequestDto) {
        return authService.reissue(tokenRequestDto);
    }

}
