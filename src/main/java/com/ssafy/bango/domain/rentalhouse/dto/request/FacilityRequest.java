package com.ssafy.bango.domain.rentalhouse.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FacilityRequest {
    @NonNull
    @Schema(description = "주택 ID")
    private Integer houseId;

    @NonNull
    @Schema(description = "경도 (longitude)")
    private String x;

    @NonNull
    @Schema(description = "위도 (latitude)")
    private String y;
}
