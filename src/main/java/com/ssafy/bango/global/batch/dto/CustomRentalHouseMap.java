package com.ssafy.bango.global.batch.dto;

import com.ssafy.bango.domain.rentalhouse.dto.response.GeoPointResponse;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.service.KakaoGeocodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
public class CustomRentalHouseMap extends PropertyMap<RentalHouseApiResponse, RentalHouse> {
    private final KakaoGeocodingService geocodingService;

    // RentalHouseApiResponse -> RentalHouse 매핑 규칙 정의
    @Override
    protected void configure() {
        Converter<RentalHouseApiResponse, String> latitudeConverter = context -> {
            String address = context.getSource().getRnAdres();
            GeoPointResponse geo = getGeoFromAddress(address);
            return geo != null ? geo.latitude() : null;
        };

        Converter<RentalHouseApiResponse, String> longitudeConverter = context -> {
            String address = context.getSource().getRnAdres();
            GeoPointResponse geo = getGeoFromAddress(address);
            return geo != null ? geo.longitude() : null;
        };

        map(source.getRnAdres(), destination.getAddress());
        map(source.getPnu(), destination.getPnu());
        map(source.getStyleNm(), destination.getBuildStyle());
        map(source.getElvtrInstlAtNm(), destination.getHasElevator());
        map(source.getParkngCo(), destination.getParkingCount());
        map(source.getHouseTyNm(), destination.getHouseType());
        map(source.getBrtcCode(), destination.getGugunCode());
        map(source.getBrtcNm(), destination.getGugunName());
        map(source.getSignguCode(), destination.getSidoCode());
        map(source.getSignguNm(), destination.getSidoName());
        map(source.getSuplyTyNm(), destination.getSupplyType());
        map(source.getInsttNm(), destination.getInstitudeType());
        using(ctx -> parseDate(source.getCompetDe())).map(source.getCompetDe(), destination.getBuiltAt());
        using(latitudeConverter).map(source, destination.getLatitude());
        using(longitudeConverter).map(source, destination.getLongitude());
    }

    private LocalDateTime parseDate(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
        }
        return null;
    }

    private GeoPointResponse getGeoFromAddress(String address) {
        return geocodingService.getGeoFromAddress(address).orElse(null);
    }
}
