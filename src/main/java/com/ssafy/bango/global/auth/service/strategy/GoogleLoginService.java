package com.ssafy.bango.global.auth.service.strategy;

import com.ssafy.bango.domain.member.dto.Member;
import com.ssafy.bango.global.auth.fegin.google.GoogleApiClient;
import com.ssafy.bango.global.auth.fegin.google.GoogleAuthApiClient;
import com.ssafy.bango.global.auth.fegin.google.dto.response.GoogleAccessTokenResponse;
import com.ssafy.bango.global.auth.fegin.google.dto.response.GoogleUserResponse;
import com.ssafy.bango.global.auth.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class GoogleLoginService implements SocialLoginService {
    @Value("${oauth.google.client-id}")
    private String CLIENT_ID;
    @Value("${oauth.google.client-secret}")
    private String CLIENT_SECRET;
    @Value("${oauth.google.authorization-grant-type}")
    private String GRANT_TYPE;
    @Value("${oauth.google.redirect-uri}")
    private String REDIRECT_URL;

    private final GoogleAuthApiClient googleAuthApiClient;
    private final GoogleApiClient googleApiClient;

    public String getSocialAccessToken(String code) {
        GoogleAccessTokenResponse tokenResponse = googleAuthApiClient.getOAuth2AccessToken(
                GRANT_TYPE,
                CLIENT_ID,
                CLIENT_SECRET,
                REDIRECT_URL,
                code,
                ""
        );

        return tokenResponse.getAccessToken();
    }

    public String getSocialId(String socialAccessToken) {
        GoogleUserResponse googleUser = googleApiClient.getUserInformation("Bearer " + socialAccessToken);

        return googleUser.getId();
    }

    public void setSocialInfo(Member loginMember, String socialAccessToken) {
        GoogleUserResponse googleUser = googleApiClient.getUserInformation("Bearer " + socialAccessToken);

        loginMember.setMemberInfo(googleUser.getName(), googleUser.getEmail());
    }
}
