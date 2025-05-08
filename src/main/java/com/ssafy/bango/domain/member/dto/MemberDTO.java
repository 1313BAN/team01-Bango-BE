package com.ssafy.bango.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long memberSeq;
    private String id;
    private String nickname;
    private String email;
    private String password;

    private SocialPlatform socialPlatform;     // LOCAL, GOOGLE, KAKAO...
    private String providerId;   // 소셜 유저일 때

    
    private String roles;        // comma-separated
}
