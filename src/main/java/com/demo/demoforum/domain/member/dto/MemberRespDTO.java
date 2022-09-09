package com.demo.demoforum.domain.member.dto;

import com.demo.demoforum.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRespDTO {
    private String username;

    public static MemberRespDTO of(Member member) {
        return new MemberRespDTO(member.getUsername());
    }
}
