package com.demo.demoforum.feature.question;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/questions")
@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questions = questionService.getQuestions();
        model.addAttribute("questions", questions);
        return "questions/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        Question qusetion = questionService.getQusetion(id);
        model.addAttribute("question", qusetion);
        return "questions/detail";
    }

    @GetMapping("/create")
    public String questionForm(QuestionFormDto questionFormDto) {
        return "questions/form";
    }

    @PostMapping("/create")
    public String createQuestion(@Valid QuestionFormDto questionFormDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "questions/form";
        }
        questionService.create(questionFormDto.getSubject(), questionFormDto.getContent());
        return "redirect:/questions/list";
    }
}
