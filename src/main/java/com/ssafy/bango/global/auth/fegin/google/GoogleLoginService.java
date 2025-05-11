package com.ssafy.bango.global.auth.fegin.google;

import com.ssafy.bango.domain.member.dto.Member;
import com.ssafy.bango.global.auth.fegin.google.dto.response.GoogleAccessTokenResponse;
import com.ssafy.bango.global.auth.fegin.google.dto.response.GoogleUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class GoogleLoginService {
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

    public String getGoogleAccessToken(String code) {
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

    public String getGoogleId(String socialAccessToken) {
        GoogleUserResponse googleUser = googleApiClient.getUserInformation("Bearer " + socialAccessToken);

        return googleUser.getId();
    }

    public void setGoogleInfo(Member loginMember, String socialAccessToken) {
        GoogleUserResponse googleUser = googleApiClient.getUserInformation("Bearer " + socialAccessToken);

        loginMember.setMemberInfo(googleUser.getName(), googleUser.getEmail());
    }
}
