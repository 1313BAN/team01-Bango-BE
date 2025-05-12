package com.ssafy.bango.global.auth.service;

import com.ssafy.bango.domain.member.entity.Member;

public interface SocialLoginService {
    String getSocialAccessToken(String code);

    String getSocialId(String socialAccessToken);

    void setSocialInfo(Member loginMember, String socialAccessToken);
}
