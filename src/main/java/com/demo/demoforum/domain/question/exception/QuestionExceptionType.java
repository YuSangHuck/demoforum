package com.demo.demoforum.domain.question.exception;

import com.demo.demoforum.global.exception.BaseExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum QuestionExceptionType implements BaseExceptionType {
    NOT_FOUND_QUESTION("NOT_FOUND_QUESTION", "존재하지 않는 질문 입니다.", HttpStatus.BAD_REQUEST);
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
