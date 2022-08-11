package com.demo.demoforum.feature.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void 저장() {
        // given
        Question q1 = Question.builder()
                .subject("forum이란?")
                .content("forum에 대해 알고싶다.")
                .build();
        Question q2 = Question.builder()
                .subject("start_value not found")
                .content("Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException\n" +
                        "왜이러는걸까요?..." +
                        "+ 예 local h2 version은 1.4.2 /// 어플리케이션 h2 version은 2.x.x ㅋㅋ")
                .build();
        Question q3 = Question.builder()
                .subject("JdbcSQLDataException")
                .content("""
                        Caused by: org.h2.jdbc.JdbcSQLDataException: Value too long for column "SUBJECT CHARACTER VARYING(20)": "'Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException' (50)"; SQL statement:

                        테스트에 @Transactional을 붙이니 사라지는데 왜이러는걸까요?...@Transactional 이 문제가 아니라 그냥 subject 에 들어간느 value가 너무 길어서 그런거임 히히@Transactional 붙이면 왜 안뜨는진 모르겟네""")
                .build();

        // when
        Question save1 = questionRepository.save(q1);
        Question save2 = questionRepository.save(q2);
        Question save3 = questionRepository.save(q3);

        // then

        assertThat(save1).isEqualTo(q1);
        assertThat(save2).isEqualTo(q2);
        assertThat(save3).isEqualTo(q3);
    }
    @Test
    public void 전부조회() {
        // given
        Question q1 = Question.builder()
                .subject("제목1")
                .content("내용1")
                .build();
        Question q2 = Question.builder()
                .subject("제목2")
                .content("내용2")
                .build();
        Question save1 = questionRepository.save(q1);
        questionRepository.save(q2);
        // when
        List<Question> all = questionRepository.findAll();

        // then
        assertThat(2).isEqualTo(all.size());
        assertThat("제목1").isEqualTo(save1.getSubject());
    }

    @Test
    public void 제목으로하나조회() {
        // given
        Question question = Question.builder()
                .subject("하나만제목")
                .content("내용")
                .build();
        questionRepository.save(question);

        // when
        Question find = questionRepository.findBySubject("하나만제목");

        // then
        assertThat(find).isEqualTo(question);
    }
    @Test
    public void 제목내용으로하나조회() {
        // given
        Question question = Question.builder()
                .subject("제목내용으로하나조회")
                .content("제목내용으로하나조회 내용")
                .build();
        Question save = questionRepository.save(question);

        // when
        Question find = questionRepository.findBySubjectAndContent("제목내용으로하나조회", "제목내용으로하나조회 내용");

        // then
        assertThat(find).isEqualTo(save);
    }
}