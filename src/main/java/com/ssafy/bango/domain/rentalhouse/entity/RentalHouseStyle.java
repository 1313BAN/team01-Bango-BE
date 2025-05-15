package com.ssafy.bango.domain.rentalhouse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.bango.global.batch.dto.RentalHouseApiResponse;
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

  @Column(length = 30)
  private String buildStyle;

  private Integer baseDeposit;

  private Integer baseMonthlyRent;

  private Integer baseConversionDeposit;

  @Column(length = 45)
  private String styleName;

  private Float supplyPrivateArea;

  private Float supplyPublicArea;

  public static RentalHouseStyle from(RentalHouseApiResponse dto, RentalHouse house) {
    return RentalHouseStyle.builder()
        .rentalHouse(house)
        .buildStyle(dto.getStyleNm())
        .styleName(dto.getStyleNm())
        .baseDeposit(dto.getBassRentGtn())
        .baseMonthlyRent(dto.getBassMtRntchrg())
        .baseConversionDeposit(dto.getBassCnvrsGtnLmt())
        .supplyPrivateArea(convertToFloat(dto.getSuplyPrvuseAr()))
        .supplyPublicArea(convertToFloat(dto.getSuplyCmnuseAr()))
        .build();
  }

  private static Float convertToFloat(Double value) {
    return value != null ? value.floatValue() : null;
  }
}
