package com.demo.demoforum.feature.answer;

import com.demo.demoforum.feature.question.Question;
import com.demo.demoforum.feature.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content, SiteUser author) {
        Answer answer = Answer.builder()
                .content(content)
                .question(question)
                .author(author)
                .build();
        answerRepository.save(answer);
    }
}
