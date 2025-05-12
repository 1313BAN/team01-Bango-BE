package com.ssafy.bango.domain.member.dto.request;

import com.ssafy.bango.domain.member.dto.SocialPlatform;
import jakarta.validation.constraints.NotNull;

public record GetAccessTokenRequest(
        @NotNull(message = "소셜 플랫폼은 필수 입니다.")
        SocialPlatform socialPlatform,

        @NotNull(message = "code는 필수 입니다.")
        String code) {
}
