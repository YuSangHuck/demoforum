package com.demo.demoforum.feature.question;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String questionForm() {
        return "questions/form";
    }

    @PostMapping("/create")
    public String createQuestion(QuestionForm questionForm) {
        questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/questions/list";
    }
}
