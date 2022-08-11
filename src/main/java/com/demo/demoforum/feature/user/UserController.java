package com.demo.demoforum.feature.user;

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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm(UserFormDto userFormDto) {
        return "users/form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserFormDto userFormDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/form";
        }
        if (!(userFormDto.getPassword1().equals(userFormDto.getPassword2()))) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "users/form";
        }

        try {
            userService.join(userFormDto.getEmail(), userFormDto.getPassword1(), userFormDto.getUsername());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다");
            return "users/form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "users/form";
        }

        return "redirect:/";
    }
}
