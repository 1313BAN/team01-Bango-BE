package com.ssafy.bango.domain.rentalhouse.dto.request;

import com.ssafy.bango.global.validation.ValidLatLongBounds;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidLatLongBounds
public class LatLongBoundsRequest {
    @NotNull(message = "최소 위도는 필수입니다.")
    @DecimalMin(value = "37.4", message = "서울 범위의 최소 위도여야 합니다.")
    @DecimalMax(value = "37.7", message = "서울 범위의 최대 위도여야 합니다.")
    private Double minLatitude;

    @NotNull(message = "최대 위도는 필수입니다.")
    @DecimalMin(value = "37.4", message = "서울 범위의 최소 위도여야 합니다.")
    @DecimalMax(value = "37.7", message = "서울 범위의 최대 위도여야 합니다.")
    private Double maxLatitude;

    @NotNull(message = "최소 경도는 필수입니다.")
    @DecimalMin(value = "126.7", message = "서울 범위의 최소 경도여야 합니다.")
    @DecimalMax(value = "127.2", message = "서울 범위의 최대 경도여야 합니다.")
    private Double minLongitude;

    @NotNull(message = "최대 경도는 필수입니다.")
    @DecimalMin(value = "126.7", message = "서울 범위의 최소 경도여야 합니다.")
    @DecimalMax(value = "127.2", message = "서울 범위의 최대 경도여야 합니다.")
    private Double maxLongitude;
}
