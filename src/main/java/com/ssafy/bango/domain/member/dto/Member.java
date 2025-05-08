package com.ssafy.bango.domain.member.dto;

import com.ssafy.bango.domain.noticelikes.dto.NoticeLike;
import com.ssafy.bango.global.auth.dto.RefreshToken;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
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

    private LocalDateTime createdAt;

    @Column(nullable = false, length = 45)
    private String role;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeLike> likes;
}
