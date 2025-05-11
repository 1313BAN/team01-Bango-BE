package com.ssafy.bango.domain.member.controller.dto.request;

import com.ssafy.bango.domain.member.dto.SocialPlatform;

public record GetAccessTokenRequest(SocialPlatform socialPlatform, String code) {
}
