package com.ssafy.bango.domain.member.dto.request;

import com.ssafy.bango.domain.member.entity.SocialPlatform;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @NotNull(message = "social accessToken은 필수 입니다.")
        String socialAccessToken,

        @NotNull(message = "소셜 플랫폼은 필수 입니다.")
        SocialPlatform socialPlatform) {
}
