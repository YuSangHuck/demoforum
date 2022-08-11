package com.demo.demoforum.feature.answer;

import com.demo.demoforum.feature.question.Question;
import com.demo.demoforum.feature.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String create(@PathVariable Long id, @Valid AnswerFormDto answerFormDto, BindingResult bindingResult, Model model) {
        Question question = questionService.getQusetion(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "questions/detail";
        }
        answerService.create(question, answerFormDto.getContent());
        return String.format("redirect:/questions/detail/%s", id);
    }
}
