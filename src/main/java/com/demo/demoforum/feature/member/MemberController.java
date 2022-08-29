package com.demo.demoforum.feature.member;

import com.demo.demoforum.exception.BaseExceptionType;
import com.demo.demoforum.exception.BizException;
import com.demo.demoforum.feature.auth.AuthService;
import com.demo.demoforum.feature.jwt.TokenRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    @GetMapping("/signup")
    public String signupForm(MemberFormDto memberFormDto) {
        return "members/signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberFormDto memberFormDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/signup_form";
        }
        if (!(memberFormDto.getPassword1().equals(memberFormDto.getPassword2()))) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "members/signup_form";
        }

        try {
            memberService.join(memberFormDto.getEmail(), memberFormDto.getPassword1(), memberFormDto.getUsername());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다");
            return "members/signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "members/signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/signin")
    public String signForm(SigninDTO signinDTO) {
        return "members/signin_form";
    }


    @PostMapping("/signin")
    public String signin(@Valid @ModelAttribute SigninDTO signinDTO, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "members/signin_form";
        }

        try {
            TokenRespDTO tokenRespDTO = authService.signin(signinDTO);
            //쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료)
            Cookie accessToken = new Cookie("accessToken", tokenRespDTO.getAccessToken());
            accessToken.setMaxAge(tokenRespDTO.getAccessTokenTime());
            accessToken.setPath("/");
            response.addCookie(accessToken);
            Cookie refreshToken = new Cookie("refreshToken", tokenRespDTO.getRefreshToken());
            refreshToken.setMaxAge(tokenRespDTO.getRefreshTokenTime());
            refreshToken.setPath("/");
            response.addCookie(refreshToken);
        } catch (BizException e) {
            e.printStackTrace();
            BaseExceptionType baseExceptionType = e.getBaseExceptionType();
            bindingResult.reject(baseExceptionType.getErrorCode(), baseExceptionType.getMessage());
            return "members/signin_form";
        }
        return "redirect:/";
    }
}
