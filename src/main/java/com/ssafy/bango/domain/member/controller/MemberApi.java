package com.ssafy.bango.domain.member.controller;

import com.ssafy.bango.domain.member.dto.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface MemberApi {
    ResponseEntity<Member> me(Authentication auth);
}
