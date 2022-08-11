package com.demo.demoforum.feature.answer;

import com.demo.demoforum.feature.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = Answer.builder()
                .Content(content)
                .question(question)
                .build();
        answerRepository.save(answer);
    }
}
