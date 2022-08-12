package com.demo.demoforum.feature.answer;

import com.demo.demoforum.feature.question.Question;
import com.demo.demoforum.feature.question.QuestionService;
import com.demo.demoforum.feature.user.SiteUser;
import com.demo.demoforum.feature.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
        Question question = questionService.getQuestion(id);
        SiteUser siteUser = userService.searchUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "questions/detail";
        }
        answerService.create(question, answerFormDto.getContent(), siteUser);
        return String.format("redirect:/questions/detail/%s", id);
    }
//    FIXME createForm, modifyForm / create, modify 함수명 구분
//    FIXME return template 파일명, redirect 변수로 잘좀
//    FIXME dto, entity / controller, service 레이어 잘 나눌것. cqs
//    FIXME 에러도 문자열 말고 뭐 안되나? i18n 엮어서 봐볼것
//    FIXME 중복된거 공통으로 앞단에서 처리 못하나? 앞에 필터두고 필터별로 메소드 인젝션해주고
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(@PathVariable Long id, AnswerFormDto answerFormDto, Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerFormDto.setContent(answer.getContent());
        return "answers/answer_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerFormDto answerForm, BindingResult bindingResult,
                               @PathVariable Long id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answers/answer_form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/questions/detail/%s", answer.getQuestion().getId());
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(@PathVariable("id") Long id, Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer);
        return String.format("redirect:/questions/detail/%s", answer.getQuestion().getId());
    }
//    !FIXME
}
