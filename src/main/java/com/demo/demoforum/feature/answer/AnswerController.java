package com.demo.demoforum.feature.answer;

import com.demo.demoforum.feature.question.Question;
import com.demo.demoforum.feature.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String create(@PathVariable Long id, @RequestParam String content) {
        Question question = questionService.getQusetion(id);
        answerService.create(question, content);
        return String.format("redirect:/questions/detail/%s", id);
    }
}
