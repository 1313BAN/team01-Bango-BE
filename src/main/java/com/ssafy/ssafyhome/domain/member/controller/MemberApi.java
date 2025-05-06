package com.ssafy.ssafyhome.domain.member.controller;

import com.ssafy.ssafyhome.domain.member.dto.MemberDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface MemberApi {
    ResponseEntity<MemberDTO> me(Authentication auth);
}
