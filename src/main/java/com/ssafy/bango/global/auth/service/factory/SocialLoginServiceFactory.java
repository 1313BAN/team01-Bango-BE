package com.ssafy.bango.global.auth.service.factory;

import com.ssafy.bango.domain.member.entity.SocialPlatform;
import com.ssafy.bango.global.auth.service.SocialLoginService;
import com.ssafy.bango.global.auth.service.strategy.GoogleLoginService;
import com.ssafy.bango.global.auth.service.strategy.KakaoLoginService;
import com.ssafy.bango.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.ssafy.bango.global.exception.enums.ErrorType.INVALID_SOCIAL_PLATFORM_ERROR;

@Component
@RequiredArgsConstructor
public class SocialLoginServiceFactory {
    private final GoogleLoginService googleLoginService;
    private final KakaoLoginService kakaoLoginService;


    public SocialLoginService getService(SocialPlatform socialPlatform) {
        return switch (socialPlatform) {
            case GOOGLE -> googleLoginService;
            case KAKAO -> kakaoLoginService;
            default -> throw new CustomException(INVALID_SOCIAL_PLATFORM_ERROR);
        };
    }
}
