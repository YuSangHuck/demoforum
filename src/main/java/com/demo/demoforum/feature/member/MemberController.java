package com.demo.demoforum.feature.member;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

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
    public String signForm() {
        return "members/signin_form";
    }
}
