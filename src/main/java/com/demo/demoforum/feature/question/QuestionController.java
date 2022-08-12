package com.demo.demoforum.feature.question;

import com.demo.demoforum.feature.answer.AnswerFormDto;
import com.demo.demoforum.feature.user.SiteUser;
import com.demo.demoforum.feature.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/questions")
@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;
    private final String QUESTION_FORM = "/questions/form";
    private final String QUESTION_LIST = "/questions/list";
    private final String QUESTION_DETAIL = "/questions/detail";
    private final String REDIRECT = "redirect:";

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = questionService.getQuestions(page);
        model.addAttribute("paging", paging);
        return QUESTION_LIST;
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id, AnswerFormDto answerFormDto) {
        Question qusetion = questionService.getQuestion(id);
        model.addAttribute("question", qusetion);
        return QUESTION_DETAIL;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionForm(QuestionFormDto questionFormDto) {
        return QUESTION_FORM;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createQuestion(@Valid QuestionFormDto questionFormDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return QUESTION_FORM;
        }
        SiteUser siteUser = userService.searchUser(principal.getName());
        questionService.create(questionFormDto.getSubject(), questionFormDto.getContent(), siteUser);
        return REDIRECT + QUESTION_LIST;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionFormDto questionFormDto, @PathVariable Long id, Principal principal) {
        Question qusetion = questionService.getQuestion(id);
        if (!qusetion.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }
        log.info("question: {}", qusetion);
        questionFormDto.setSubject(qusetion.getSubject());
        questionFormDto.setContent(qusetion.getContent());
        return QUESTION_FORM;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionFormDto questionFormDto, @PathVariable Long id, Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return QUESTION_FORM;
        }
        Question qusetion = questionService.getQuestion(id);
        if (!qusetion.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }
        questionService.modify(qusetion, questionFormDto.getSubject(), questionFormDto.getContent());
        return REDIRECT + QUESTION_DETAIL + "/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(@PathVariable Long id, Principal principal) {
        Question question = questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");

        }
        questionService.delete(question);
        return REDIRECT + "/";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(@PathVariable Long id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.searchUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/questions/detail/%s", id);
    }
}
