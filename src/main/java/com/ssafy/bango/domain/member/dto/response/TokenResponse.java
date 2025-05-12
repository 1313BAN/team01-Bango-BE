package com.ssafy.bango.domain.member.dto.response;

import com.ssafy.bango.global.auth.jwt.TokenDTO;

public record TokenResponse(Long memberId, TokenDTO token) {
    public static TokenResponse of(Long memberId, TokenDTO token) {
        return new TokenResponse(memberId, token);
    }
}
