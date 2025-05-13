package com.ssafy.bango.domain.member.controller;

import com.ssafy.bango.domain.member.dto.request.GetAccessTokenRequest;
import com.ssafy.bango.domain.member.dto.request.LoginRequest;
import com.ssafy.bango.domain.member.dto.response.TokenResponse;
import com.ssafy.bango.domain.member.entity.Member;
import com.ssafy.bango.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface MemberApi {
    ResponseEntity<ApiResponse<TokenResponse>> login(LoginRequest loginRequest);

    ResponseEntity<ApiResponse<Member>> me(Authentication auth);

    ResponseEntity<ApiResponse<String>> getAccessToken(GetAccessTokenRequest getAccessTokenRequest);
}
