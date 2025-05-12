package com.ssafy.bango.domain.rentalhouse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalHouseStyle {
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
