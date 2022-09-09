package com.demo.demoforum.domain.answer.service;

import com.demo.demoforum.domain.answer.entity.Answer;
import com.demo.demoforum.domain.answer.exception.AnswerExceptionType;
import com.demo.demoforum.domain.answer.repository.AnswerRepository;
import com.demo.demoforum.domain.member.entity.Member;
import com.demo.demoforum.domain.question.entity.Question;
import com.demo.demoforum.global.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content, Member author) {
        Answer answer = Answer.builder()
                .content(content)
                .question(question)
                .author(author)
                .build();
        return answerRepository.save(answer);
    }

    //FIXME exception 문자열 변수로
    public Answer getAnswer(Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new BizException(AnswerExceptionType.NOT_FOUND_ANSWER));
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public void vote(Answer answer, Member member) {
        answer.getVoter().add(member);
        this.answerRepository.save(answer);
    }
}
