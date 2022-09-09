package com.demo.demoforum.domain.answer.repository;

import com.demo.demoforum.domain.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion_Id(Long id);
}
