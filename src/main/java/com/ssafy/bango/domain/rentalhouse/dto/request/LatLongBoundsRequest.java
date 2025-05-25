package com.ssafy.bango.domain.rentalhouse.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LatLongBoundsRequest {
    @NotNull(message = "최소 위도는 필수입니다.")
    @DecimalMin(value = "33.0", message = "대한민국 범위의 최소 위도여야 합니다.")
    @DecimalMax(value = "43.0", message = "대한민국 범위의 최대 위도여야 합니다.")
    private Double minLatitude;

    @NotNull(message = "최대 위도는 필수입니다.")
    @DecimalMin(value = "33.0", message = "대한민국 범위의 최소 위도여야 합니다.")
    @DecimalMax(value = "43.0", message = "대한민국 범위의 최대 위도여야 합니다.")
    private Double maxLatitude;

    @NotNull(message = "최소 경도는 필수입니다.")
    @DecimalMin(value = "124.0", message = "대한민국 범위의 최소 경도여야 합니다.")
    @DecimalMax(value = "132.0", message = "대한민국 범위의 최대 경도여야 합니다.")
    private Double minLongitude;

    @NotNull(message = "최대 경도는 필수입니다.")
    @DecimalMin(value = "124.0", message = "대한민국 범위의 최소 경도여야 합니다.")
    @DecimalMax(value = "132.0", message = "대한민국 범위의 최대 경도여야 합니다.")
    private Double maxLongitude;
}
