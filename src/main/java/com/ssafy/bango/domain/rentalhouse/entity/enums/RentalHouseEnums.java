package com.ssafy.bango.domain.rentalhouse.entity.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RentalHouseEnums {

    @Getter
    @AllArgsConstructor
    public enum SignguCode {
        JONGNO_GU("110"),
        JUNG_GU("140"),
        YONGSAN_GU("170"),
        SEONGDONG_GU("200"),
        GWANGJIN_GU("215"),
        DONGDAEMUN_GU("230"),
        JUNGNANG_GU("260"),
        SEONGBUK_GU("290"),
        GANGBUK_GU("305"),
        DOBONG_GU("320"),
        NOWON_GU("350"),
        EUNPYEONG_GU("380"),
        SEODAEMUN_GU("410"),
        MAPO_GU("440"),
        YANGCHEON_GU("470"),
        GANGSEO_GU("500"),
        GURO_GU("530"),
        GEUMCHEON_GU("545"),
        YEONGDEUNGPO_GU("560"),
        DONGJAK_GU("590"),
        GWANAK_GU("620"),
        SEOCHO_GU("650"),
        GANGNAM_GU("680"),
        SONGPA_GU("710"),
        GANGDONG_GU("740");

        private final String code;
    }

    @Getter
    @RequiredArgsConstructor
    public enum RentalHouseResponseMessage {
        eGetRentalHouseSuccess("서울특별시 구의 공공임대주택 개수를 가져왔습니다."),
        eGetWantedRentalHouseSuccess("해당 구의 공공임대주택을 가져왔습니다."),
        eGetRentalHouseDetailSuccess("해당 공고의 상세 정보를 가져왔습니다.");
        private final String message;
    }
}