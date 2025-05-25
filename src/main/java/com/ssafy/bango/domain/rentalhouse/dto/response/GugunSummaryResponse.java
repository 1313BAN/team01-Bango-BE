package com.ssafy.bango.domain.rentalhouse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GugunSummaryResponse {
    private String gugunCode;
    private String gugunName;
    private Long rentalCount;
    private Double avgLatitude;
    private Double avgLongitude;
}
