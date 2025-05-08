package com.ssafy.bango.domain.rentalnotice.dto;

import com.ssafy.bango.domain.noticelikes.dto.NoticeLike;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rental_notice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalNotice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer noticeId;

  @Column(length = 10)
  private String institutionType;

  @Column(length = 20)
  private String status;

  @Column(length = 30)
  private String houseType;

  @Column(length = 30)
  private String supplyType;

  private LocalDateTime createdDate;

  private LocalDateTime announceDate;

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

  private LocalDateTime beginDate;

  private LocalDateTime endDate;

  @OneToMany(mappedBy = "rentalNotice", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<NoticeLike> likes;
}
