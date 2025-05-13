package com.ssafy.bango.domain.member.service;

import com.ssafy.bango.domain.member.dto.request.GetAccessTokenRequest;
import com.ssafy.bango.domain.member.dto.request.LoginRequest;
import com.ssafy.bango.domain.member.dto.request.ReissueRequest;
import com.ssafy.bango.domain.member.dto.response.MemberInfoResponse;
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

import java.security.Principal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final OAuthService oAuthService;

    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        String accessToken = loginRequest.socialAccessToken();
        SocialPlatform socialPlatform = loginRequest.socialPlatform();

        String socialId = oAuthService.getSocialId(socialPlatform, accessToken);

        // DB에 유저 정보를 조회하고, 없을 경우 회원 가입
        Member loginMember = memberRepository
                .getMemberBySocialId(socialId)
                .orElseGet(() -> signUp(socialPlatform, socialId, accessToken));

        // 토큰 발행
        TokenDTO tokenDTO = jwtProvider.issueToken(
                new UserAuthentication(loginMember.getMemberId(), null, null)
        );

        return TokenResponse.of(loginMember.getMemberId(), tokenDTO);
    }

    /**
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

    public ApiResponse<String> getSocialAccessToken(GetAccessTokenRequest getAccessTokenRequest) {
        return oAuthService.getSocialAccessToken(
                getAccessTokenRequest.socialPlatform(),
                getAccessTokenRequest.code()
        );
    }

    @Transactional
    public void logout(Principal principal) {
        jwtProvider.deleteRefreshToken(jwtProvider.getMemberIdFromPrincipal(principal));
    }

    public MemberInfoResponse me(Principal principal) {
        Long memberId = jwtProvider.getMemberIdFromPrincipal(principal);
        Member member = memberRepository.getMemberByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_MEMBER_ERROR));

        return MemberInfoResponse.of(
                member.getName(),
                member.getEmail(),
                member.getSocialPlatform()
        );
    }

    @Transactional
    public void withdraw(Principal principal) {
        Long memberId = jwtProvider.getMemberIdFromPrincipal(principal);
        memberRepository.deleteById(memberId);
        jwtProvider.deleteRefreshToken(memberId);
    }

    @Transactional
    public TokenResponse reissueToken(ReissueRequest reissueRequest) {
        Long memberId = jwtProvider.validateRefreshToken(reissueRequest.refreshToken());

        memberRepository.getMemberByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_MEMBER_ERROR));

        jwtProvider.deleteRefreshToken(memberId);

        return TokenResponse.of(memberId, jwtProvider.issueToken(new UserAuthentication(memberId, null, null)));
    }
}
