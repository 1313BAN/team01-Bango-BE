package com.ssafy.bango.domain.member.dto.response;

import com.ssafy.bango.domain.member.entity.SocialPlatform;

public record MemberInfoResponse(
        String name,
        String email,
        SocialPlatform socialPlatform
) {
    public static MemberInfoResponse of(String name, String email, SocialPlatform socialPlatform) {
        return new MemberInfoResponse(name, email, socialPlatform);
    }
}
