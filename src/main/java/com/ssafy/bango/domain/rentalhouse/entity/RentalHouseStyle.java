package com.ssafy.bango.domain.rentalhouse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.bango.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalHouseStyle extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer styleId;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "house_id", nullable = false)
  private RentalHouse rentalHouse;

  private Integer baseDeposit;

  private Integer baseMonthlyRent;

  private Integer baseConversionDeposit;

  @Column(length = 45)
  private String styleName;

  private Float supplyPrivateArea;

  private Float supplyPublicArea;
}
