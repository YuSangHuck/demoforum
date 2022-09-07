package com.demo.demoforum.feature.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TokenReqDTO {
    private String accessToken;
    private String refreshToken;
}
