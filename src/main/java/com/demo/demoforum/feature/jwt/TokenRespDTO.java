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
    private int accessTokenTime;
    private String refreshToken;
    private int refreshTokenTime;
}
