package com.ssafy.bango.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull(message = "social accessToken은 필수 입니다.")
        String socialAccessToken) {
}
