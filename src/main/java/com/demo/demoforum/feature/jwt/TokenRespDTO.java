package com.demo.demoforum.feature.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TokenRespDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
