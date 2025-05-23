package com.ssafy.bango.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.bango.domain.noticelike.entity.NoticeLike;
import com.ssafy.bango.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 45)
    private String name;

    @Column(length = 45, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialPlatform socialPlatform;

    @Column(nullable = false, length = 100)
    private String socialId;

//    @Column(length = 45)
//    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeLike> likes;

    public void setMemberInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
