package com.demo.demoforum.domain.answer.repository;

import com.demo.demoforum.domain.answer.entity.Answer;
import com.demo.demoforum.domain.question.entity.Question;
import com.demo.demoforum.domain.question.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    private Question _question; // id는 1L


    @BeforeEach
    public void initQuestion() {
        _question = questionRepository.findById(1L).orElse(
                questionRepository.save(
                        Question.builder()
                                .subject("initQuestionSubject")
                                .content("initQuestionContent")
                                .build()
                )
        );
    }

    @Test
    public void 답변생성() {
        // given
        Answer answer = Answer.builder()
                .content("답변입니다")
                .question(_question)
                .build();

        // when
        Answer save = answerRepository.save(answer);

        // then
        assertEquals(answer, save);
    }

    @Test
    public void 답변조회() throws Exception {
        // given

        // when
        Answer find = answerRepository.findById(1L).orElseThrow(Exception::new);

        // then
        assertEquals(find.getId(), 1L);
    }

    @Test
    @Transactional
    public void 답변에연결된질문_질문에연결된답변() {
        // given
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Answer answer = Answer.builder()
                    .content("답변" + i)
                    .question(_question)
                    .build();
            answerRepository.save(answer);
            answers.add(answer);
        }

        // when
        List<Answer> answersFromQusetion = _question.getAnswers();
        List<Answer> answersFromAnswer = answerRepository.findByQuestion_Id(answers.get(0).getQuestion().getId());

        // then
        assertEquals(answersFromAnswer.size(), answersFromQusetion.size());
    }
}