package com.ssafy.bango.global.auth.fegin.kakao;

import com.ssafy.bango.domain.member.dto.Member;
import com.ssafy.bango.global.auth.fegin.kakao.dto.response.KakaoAccessTokenResponse;
import com.ssafy.bango.global.auth.fegin.kakao.dto.response.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginService {
    @Value("${oauth.kakao.client-id}")
    private String CLIENT_ID;
    @Value("${oauth.kakao.authorization-grant-type}")
    private String GRANT_TYPE;
    @Value("${oauth.kakao.redirect-uri}")
    private String REDIRECT_URL;

    private final KakaoAuthApiClient kakaoAuthApiClient;
    private final KakaoApiClient kakaoApiClient;

    public String getKakaoAccessToken(String code) {

        // Authorization code로 카카오 Access Token 불러오기
        KakaoAccessTokenResponse tokenResponse = kakaoAuthApiClient.getOAuth2AccessToken(
                GRANT_TYPE,
                CLIENT_ID,
                REDIRECT_URL,
                code
        );

        // Refresh Token은 사용 안함
        return tokenResponse.getAccessToken();
    }

    public String getKakaoId(String socialAccessToken) {
        KakaoUserResponse userResponse = kakaoApiClient.getUserInfomation("Bearer " + socialAccessToken);

        return Long.toString(userResponse.getId());
    }

    public void setKakaoInfo(Member loginMember, String socialAccessToken) {
        // Access Token으로 유저 정보 불러와서 설정
        KakaoUserResponse kakaoUser = kakaoApiClient.getUserInfomation("Bearer " + socialAccessToken);

        loginMember.setMemberInfo(
                kakaoUser.getKakaoAccount().getName(),
                kakaoUser.getKakaoAccount().getEmail()
        );
    }
}
