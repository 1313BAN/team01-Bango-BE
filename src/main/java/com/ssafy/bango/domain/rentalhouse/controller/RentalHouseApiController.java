package com.ssafy.bango.domain.rentalhouse.controller;

import com.ssafy.bango.domain.rentalhouse.dto.GeoPoint;
import com.ssafy.bango.domain.rentalhouse.service.GeocodingService;
import com.ssafy.bango.domain.rentalhouse.service.RentalHouseApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RentalHouseApiController {

//    private final RentalHouseApiService rentalHouseApiService;
    private final GeocodingService geocodingService;

//    @GetMapping("/fetch-and-save")
//    public String fetchAndSaveRentalHouses() {
//        rentalHouseApiService.fetchAndSaveFromOpenApi();
//        return "success";
//    }

    @GetMapping("/address-point")
    public Optional<GeoPoint> getGeoFromAddress(@RequestParam String address) {
        return geocodingService.getGeoFromAddress(address);
    }
}