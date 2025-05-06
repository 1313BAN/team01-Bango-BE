package com.ssafy.ssafyhome.domain.member.controller;

import com.ssafy.ssafyhome.domain.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController implements MemberApi {
    private final MemberService memberService;

    /*
     * 나중에 JWT로 바꾸면, token으로부터 정보 얻어와야할듯
     */
    @GetMapping("/me")
    public ResponseEntity<MemberDTO> me(Authentication auth) {
        MemberDTO member = memberService.findById(auth.getName());
        // 나중에 DTO 따로 만들어서 필요한 정보만 넘겨주게 해야될듯
        return ResponseEntity.ok(member);
    }

//    @DeleteMapping
//    public ResponseEntity<?> withdraw(Authentication auth) {
//        MemberDTO m = memberService.findById(auth.getName());
//        // 세션 무효화
//        SecurityContextHolder.clearContext();
//        return ResponseEntity.ok().build();
//    }
}

