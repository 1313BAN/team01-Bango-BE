package com.ssafy.bango.domain.member.controller;

import com.ssafy.bango.domain.member.controller.dto.request.GetAccessTokenRequest;
import com.ssafy.bango.domain.member.controller.dto.request.LoginRequest;
import com.ssafy.bango.domain.member.controller.dto.request.SignUpRequest;
import com.ssafy.bango.domain.member.controller.dto.response.TokenResponse;
import com.ssafy.bango.domain.member.dto.Member;
import com.ssafy.bango.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface MemberApi {
    ResponseEntity<ApiResponse<TokenResponse>> signup(SignUpRequest signUpRequest);

    ResponseEntity<ApiResponse<TokenResponse>> login(LoginRequest loginRequest);

    ResponseEntity<ApiResponse<Member>> me(Authentication auth);

    ResponseEntity<ApiResponse<String>> getAccessToken(GetAccessTokenRequest getAccessTokenRequest);
}
