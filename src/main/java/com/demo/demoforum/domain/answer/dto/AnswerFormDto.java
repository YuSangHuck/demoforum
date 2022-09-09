package com.demo.demoforum.domain.answer.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnswerFormDto {
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
