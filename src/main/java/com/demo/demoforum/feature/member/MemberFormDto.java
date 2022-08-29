package com.demo.demoforum.feature.member;

import com.demo.demoforum.feature.authority.Authority;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
public class MemberFormDto implements Serializable {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private final String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다")
    private final String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다")
    private final String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private final String email;

    public Member toMember(PasswordEncoder passwordEncoder, Set<Authority> set) {
        return Member.builder()
                .password(passwordEncoder.encode(password1))
                .authorities(set)
                .build();
    }
}
