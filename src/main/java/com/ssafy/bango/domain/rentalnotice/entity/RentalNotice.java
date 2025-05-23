package com.ssafy.bango.domain.rentalnotice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.bango.domain.noticelike.entity.NoticeLike;
import com.ssafy.bango.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rental_notice")
public class RentalNotice extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer noticeId;

  // 주택 공고 api 상 id (pblancId)
  private String pblancId;

  private String noticeName;

  @Column(length = 10)
  private String institutionType;

  @Column(length = 20)
  private String status;

  @Column(length = 30)
  private String houseType;

  @Column(length = 30)
  private String supplyType;

  private LocalDate createdDate;

  private LocalDate announceDate;

  @Column(length = 30)
  private String supplyHoCount;

  @Column(length = 127)
  private String contactInfo;

  @Column(length = 255)
  private String institutionUrl;

  @Column(length = 45)
  private String houseName;

  @Column(length = 30)
  private String sidoName;

  @Column(length = 30)
  private String gugunName;

  @Column(length = 20)
  private String pnu;

  private LocalDate beginDate;

  private LocalDate endDate;

  // 주택 공고 동기화 시점
  private LocalDateTime lastSyncedAt;

  @JsonIgnore
  @OneToMany(mappedBy = "rentalNotice", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<NoticeLike> likes;
}
