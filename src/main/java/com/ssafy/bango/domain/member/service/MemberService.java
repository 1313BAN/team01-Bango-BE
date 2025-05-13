package com.ssafy.bango.domain.member.service;

import com.ssafy.bango.domain.member.dto.request.GetAccessTokenRequest;
import com.ssafy.bango.domain.member.dto.request.SignUpRequest;
import com.ssafy.bango.domain.member.dto.response.TokenResponse;
import com.ssafy.bango.domain.member.repository.MemberRepository;
import com.ssafy.bango.domain.member.entity.Member;
import com.ssafy.bango.domain.member.entity.SocialPlatform;
import com.ssafy.bango.global.auth.jwt.JwtProvider;
import com.ssafy.bango.global.auth.jwt.TokenDTO;
import com.ssafy.bango.global.auth.security.UserAuthentication;
import com.ssafy.bango.global.auth.service.OAuthService;
import com.ssafy.bango.global.common.ApiResponse;
import com.ssafy.bango.global.exception.CustomException;
import com.ssafy.bango.global.exception.enums.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final OAuthService oAuthService;

    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse signUp(SignUpRequest signUpRequest) {
        String accessToken = signUpRequest.socialAccessToken();
        SocialPlatform socialPlatform = signUpRequest.socialPlatform();

        // 중복 유저 검증
        String socialId = oAuthService.getSocialId(socialPlatform, accessToken);
        if (memberRepository.existsBySocialId(socialId)) {
            throw new CustomException(ErrorType.MEMBER_ALREADY_EXIST_ERROR);
        }

        // 회원 가입
        Member signUpMember = signUp(socialPlatform, socialId, accessToken);

        TokenDTO tokenDTO = jwtProvider.issueToken(
                new UserAuthentication(signUpMember.getMemberId(), null, null)
        );

        return TokenResponse.of(signUpMember.getMemberId(), tokenDTO);
    }

    /*
     * 멤버를 생성하고, kakao/google로부터 받아온 정보 설정.
     */
    private Member signUp(SocialPlatform socialPlatform, String socialId, String accessToken) {
        Member member = Member.builder()
                .socialPlatform(socialPlatform)
                .socialId(socialId)
                .build();

        memberRepository.save(member);
        oAuthService.setSocialInfo(socialPlatform, accessToken, member);

        return member;
    }

    @Transactional
    public ApiResponse<String> getSocialAccessToken(GetAccessTokenRequest getAccessTokenRequest) {
        return oAuthService.getSocialAccessToken(
                getAccessTokenRequest.socialPlatform(),
                getAccessTokenRequest.code()
        );
    }
}
