package com.ssafy.bango.domain.member.dto;

import com.ssafy.bango.domain.noticelikes.dto.NoticeLike;
import com.ssafy.bango.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @Column(nullable = false, length = 45)
    private String name;

    private Integer age;

    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialPlatform platformType;

    @Column(nullable = false, length = 100)
    private String socialId;

    @Column(nullable = false, length = 45)
    private String role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeLike> likes;
}
