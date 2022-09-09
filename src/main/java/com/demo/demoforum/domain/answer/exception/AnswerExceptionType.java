package com.demo.demoforum.domain.answer.exception;

import com.demo.demoforum.global.exception.BaseExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum AnswerExceptionType implements BaseExceptionType {
    NOT_FOUND_ANSWER("NOT_FOUND_ANSWER", "존재하지 않는 답변 입니다.", HttpStatus.BAD_REQUEST);
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}

