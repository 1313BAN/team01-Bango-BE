package com.ssafy.bango.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record ReissueRequest(
        @NotNull
        String refreshToken) {
}
