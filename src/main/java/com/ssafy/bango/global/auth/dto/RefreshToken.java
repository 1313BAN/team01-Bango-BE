package com.ssafy.bango.global.auth.dto;

import com.ssafy.bango.domain.member.dto.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
  @Id
  @Column(name = "member_id")
  private Integer memberId;

  @Column(name = "refresh_token", nullable = false)
  private String refreshToken;

  @OneToOne
  @MapsId
  @JoinColumn(name = "member_id")
  private Member member;
}