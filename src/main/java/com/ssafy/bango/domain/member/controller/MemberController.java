package com.ssafy.bango.domain.member.controller;


import com.ssafy.bango.domain.member.dto.request.GetAccessTokenRequest;
import com.ssafy.bango.domain.member.dto.request.LoginRequest;
import com.ssafy.bango.domain.member.dto.response.MemberInfoResponse;
import com.ssafy.bango.domain.member.dto.response.TokenResponse;
import com.ssafy.bango.domain.member.service.MemberService;
import com.ssafy.bango.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.ssafy.bango.global.exception.enums.SuccessType.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController implements MemberApi {
    private final MemberService memberService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                LOGIN_SUCCESS,
                memberService.login(loginRequest)
        ));
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> me(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(
                GET_ME_SUCCESS,
                memberService.me(principal)
        ));
    }

    @Override
    @GetMapping("/token")
    public ResponseEntity<ApiResponse<String>> getAccessToken(@Valid @RequestBody GetAccessTokenRequest getAccessTokenRequest) {
        return ResponseEntity.ok(memberService.getSocialAccessToken(getAccessTokenRequest));
    }

    @Override
    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(Principal principal) {
        memberService.logout(principal);
        return ResponseEntity.ok(ApiResponse.success(LOGOUT_SUCCESS));
    }
}
