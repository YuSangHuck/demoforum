package com.demo.demoforum.feature.answer;

import com.demo.demoforum.exception.DataNotFoundException;
import com.demo.demoforum.feature.question.Question;
import com.demo.demoforum.feature.member.Member;
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
        return answerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("answer not found"));
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
