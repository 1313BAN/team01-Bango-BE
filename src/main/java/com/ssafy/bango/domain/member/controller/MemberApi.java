package com.ssafy.bango.domain.member.controller;

import com.ssafy.bango.domain.member.dto.MemberDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface MemberApi {
    ResponseEntity<MemberDTO> me(Authentication auth);
}
