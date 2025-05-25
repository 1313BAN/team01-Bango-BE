package com.ssafy.bango.domain.rentalhouse.entity;

import com.ssafy.bango.global.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalHouseSummary extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gugunCode;
    private String gugunName;
    private String dongCode;
    private String dongName;
    private Integer rentalCount;
    private Double avgLatitude;
    private Double avgLongitude;

    public static RentalHouseSummary from(String gugunCode, String gugunName, String dongCode, String dongName, Long rentalCount, double avgLatitude, double avgLongitude) {
        return RentalHouseSummary.builder()
                .gugunCode(gugunCode)
                .gugunName(gugunName)
                .dongCode(dongCode)
                .dongName(dongName)
                .rentalCount(rentalCount.intValue())
                .avgLatitude(avgLatitude)
                .avgLongitude(avgLongitude)
                .build();
    }
}