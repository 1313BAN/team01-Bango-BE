package com.ssafy.bango.domain.rentalhouse.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RentalHouseSummaryRequest {
    private String gugunCode;
    private String gugunName;
    private String dongCode;
    private String dongName;
    private Long rentalCount;
    private Double avgLatitude;
    private Double avgLongitude;
}
