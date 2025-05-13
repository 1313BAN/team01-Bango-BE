package com.ssafy.bango.domain.rentalhouse.entity.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum FacilityType {
    MT1("대형마트"),
    CS2("편의점"),
    SC4("학교"),
    SW8("지하철역"),
    BK9("은행"),
    HP8("병원"),
    PM9("약국");

    private final String description;
}