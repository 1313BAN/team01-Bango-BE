package com.ssafy.bango.domain.rentalhouse.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LatLongBoundsRequest {
    double minLatitude;
    double maxLatitude;
    double minLongitude;
    double maxLongitude;
}
