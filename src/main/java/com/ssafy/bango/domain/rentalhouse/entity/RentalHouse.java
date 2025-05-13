package com.ssafy.bango.domain.rentalhouse.entity;

import com.ssafy.bango.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalHouse extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer houseId;

  @Column(name = "institude_type", length = 45)
  private String institudeType;

  private Integer sidoCode;

  @Column(length = 30)
  private String sidoName;

  private Integer gugunCode;

  @Column(length = 30)
  private String gugunName;

  @Column(length = 255)
  private String address;

  @Column(length = 20)
  private String pnu;

  private LocalDateTime builtAt;

  @Column(length = 30)
  private String supplyType;

  @Column(length = 30)
  private String houseType;

  @Column(length = 30)
  private String buildStyle;

  @Column(length = 30)
  private String hasElevator;

  @Column(length = 30)
  private String parkingCount;

  @Column(length = 30)
  private String latitude;

  @Column(length = 30)
  private String longitude;

  @OneToMany(mappedBy = "rentalHouse", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RentalHouseStyle> styles;

  @OneToMany(mappedBy = "rentalHouse", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Facility> facilities;
}