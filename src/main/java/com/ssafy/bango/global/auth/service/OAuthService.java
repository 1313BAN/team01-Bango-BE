package com.ssafy.bango.global.auth.service;

import com.ssafy.bango.domain.member.dto.Member;
import com.ssafy.bango.domain.member.dto.SocialPlatform;
import com.ssafy.bango.global.auth.fegin.google.GoogleLoginService;
import com.ssafy.bango.global.auth.fegin.kakao.KakaoLoginService;
import com.ssafy.bango.global.common.ApiResponse;
import com.ssafy.bango.global.exception.CustomException;
import com.ssafy.bango.global.exception.enums.ErrorType;
import com.ssafy.bango.global.exception.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {
    private final GoogleLoginService googleLoginService;
    private final KakaoLoginService kakaoLoginService;

    public String getSocialId(SocialPlatform socialPlatform, String accessToken) {
        return switch (socialPlatform) {
            case GOOGLE -> googleLoginService.getGoogleId(accessToken);
            case KAKAO -> kakaoLoginService.getKakaoId(accessToken);
            default -> throw new CustomException(ErrorType.INVALID_SOCIAL_ACCESS_TOKEN);
        };
    }

    public void setSocialInfo(SocialPlatform socialPlatform, String accessToken, Member signUpMember) {
        switch (socialPlatform) {
            case GOOGLE -> googleLoginService.setGoogleInfo(signUpMember, accessToken);
            case KAKAO -> kakaoLoginService.setKakaoInfo(signUpMember, accessToken);
        }
    }

    public ApiResponse<String> getSocialAccessToken(SocialPlatform socialPlatform, String code) {
        return switch (socialPlatform) {
            case GOOGLE -> ApiResponse.success(
                    SuccessType.GOOGLE_ACCESS_TOKEN_SUCCESS,
                    googleLoginService.getGoogleAccessToken(code));
            case KAKAO -> ApiResponse.success(
                    SuccessType.KAKAO_ACCESS_TOKEN_SUCCESS,
                    kakaoLoginService.getKakaoAccessToken(code));
            default -> throw new CustomException(ErrorType.INVALID_CODE_ERROR);
        };
    }
}
