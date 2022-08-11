package com.demo.demoforum.feature.answer;

import com.demo.demoforum.feature.question.Question;
import com.demo.demoforum.feature.question.QuestionService;
import com.demo.demoforum.feature.user.SiteUser;
import com.demo.demoforum.feature.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    //    Principal: security가 제공하는 로그인한 유저정보
    //  @PreAuthorize("isAuthenticated()"): security에서 제공하는거같은데 dispatcher전에 filter에서 걸러지는듯?
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(@PathVariable Long id, @Valid AnswerFormDto answerFormDto,
                         BindingResult bindingResult, Model model, Principal principal) {
        Question question = questionService.getQusetion(id);
        SiteUser siteUser = userService.searchUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "questions/detail";
        }
        answerService.create(question, answerFormDto.getContent(), siteUser);
        return String.format("redirect:/questions/detail/%s", id);
    }
}
