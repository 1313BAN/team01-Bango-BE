package com.ssafy.bango.domain.noticelike.entity;

import com.ssafy.bango.domain.member.entity.Member;
import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import com.ssafy.bango.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeLike extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer likeId;

  @ManyToOne
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne
  @JoinColumn(name = "notice_id", nullable = false)
  private RentalNotice rentalNotice;

  private LocalDateTime likeAt;
}
