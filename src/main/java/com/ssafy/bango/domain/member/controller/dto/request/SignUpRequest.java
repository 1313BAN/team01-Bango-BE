package com.ssafy.bango.domain.member.controller.dto.request;

import com.ssafy.bango.domain.member.dto.SocialPlatform;

public record SignUpRequest(String socialAccessToken, SocialPlatform socialPlatform) {
}
