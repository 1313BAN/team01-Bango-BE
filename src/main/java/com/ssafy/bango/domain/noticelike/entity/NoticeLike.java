package com.ssafy.bango.domain.noticelike.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.bango.domain.member.entity.Member;
import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import com.ssafy.bango.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class NoticeLike extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer likeId;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "notice_id", nullable = false)
  private RentalNotice rentalNotice;

  private NoticeLike(Member member, RentalNotice rentalNotice) {
    this.member = member;
    this.rentalNotice = rentalNotice;
  }

  public static NoticeLike of(Member member, RentalNotice rentalNotice) {
    return new NoticeLike(member, rentalNotice);
  }
}
