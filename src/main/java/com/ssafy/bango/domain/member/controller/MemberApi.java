package com.ssafy.bango.domain.member.controller;

import com.ssafy.bango.domain.member.dto.request.GetAccessTokenRequest;
import com.ssafy.bango.domain.member.dto.request.LoginRequest;
import com.ssafy.bango.domain.member.dto.response.MemberInfoResponse;
import com.ssafy.bango.domain.member.dto.response.TokenResponse;
import com.ssafy.bango.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface MemberApi {
    ResponseEntity<ApiResponse<TokenResponse>> login(LoginRequest loginRequest);

    ResponseEntity<ApiResponse<?>> logout(Principal principal);

    ResponseEntity<ApiResponse<MemberInfoResponse>> me(Principal principal);

    ResponseEntity<ApiResponse<String>> getAccessToken(GetAccessTokenRequest getAccessTokenRequest);
}
