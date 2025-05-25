package com.ssafy.bango.domain.rentalhouse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DongSummaryResponse {
    private String dongCode;
    private String dongName;
    private Integer rentalCount;
    private Double avgLatitude;
    private Double avgLongitude;
}
