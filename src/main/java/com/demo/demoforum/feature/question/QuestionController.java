package com.demo.demoforum.feature.question;

import com.demo.demoforum.feature.answer.AnswerFormDto;
import com.demo.demoforum.feature.user.SiteUser;
import com.demo.demoforum.feature.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/questions")
@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> paging = questionService.getQuestions(page);
        model.addAttribute("paging", paging);
        return "questions/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id, AnswerFormDto answerFormDto) {
        Question qusetion = questionService.getQusetion(id);
        model.addAttribute("question", qusetion);
        return "questions/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionForm(QuestionFormDto questionFormDto) {
        return "questions/form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createQuestion(@Valid QuestionFormDto questionFormDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "questions/form";
        }
        SiteUser siteUser = userService.searchUser(principal.getName());
        questionService.create(questionFormDto.getSubject(), questionFormDto.getContent(), siteUser);
        return "redirect:/questions/list";
    }
}
