package com.ssafy.bango.global.auth.service;

import com.ssafy.bango.domain.member.entity.Member;
import com.ssafy.bango.domain.member.entity.SocialPlatform;
import com.ssafy.bango.global.auth.service.factory.SocialLoginServiceFactory;
import com.ssafy.bango.global.common.ApiResponse;
import com.ssafy.bango.global.exception.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {
    private final SocialLoginServiceFactory socialLoginServiceFactory;

    public String getSocialId(SocialPlatform socialPlatform, String accessToken) {
        return socialLoginServiceFactory
                .getService(socialPlatform)
                .getSocialId(accessToken);
    }

    public void setSocialInfo(SocialPlatform socialPlatform, String accessToken, Member signUpMember) {
        socialLoginServiceFactory
                .getService(socialPlatform)
                .setSocialInfo(signUpMember, accessToken);
    }

    public ApiResponse<String> getSocialAccessToken(SocialPlatform socialPlatform, String code) {
        String socialAccessToken = socialLoginServiceFactory.getService(socialPlatform).getSocialAccessToken(code);

        return ApiResponse.success(
                SuccessType.SOCIAL_ACCESS_TOKEN_SUCCESS,
                socialAccessToken
        );
    }
}
