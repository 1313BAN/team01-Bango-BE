package com.ssafy.bango.domain.noticelikes.dto;

import com.ssafy.bango.domain.member.dto.Member;
import com.ssafy.bango.domain.rentalnotice.dto.RentalNotice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeLike {
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
