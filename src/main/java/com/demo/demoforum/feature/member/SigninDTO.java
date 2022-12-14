package com.demo.demoforum.feature.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SigninDTO {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;
    @NotEmpty(message = "비밀번호는 필수항목입니다")
    private String password;
}
