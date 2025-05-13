package com.ssafy.bango.domain.rentalhouse.controller;

import com.ssafy.bango.domain.rentalhouse.dto.response.GeoPointResponse;
import com.ssafy.bango.domain.rentalhouse.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rental/api")
public class RentalHouseApiController {

//    private final RentalHouseApiService rentalHouseApiService;
    private final GeocodingService geocodingService;

//    @GetMapping("/fetch-and-save")
//    public String fetchAndSaveRentalHouses() {
//        rentalHouseApiService.fetchAndSaveFromOpenApi();
//        return "success";
//    }

    @GetMapping("/geocoding")
    public Optional<GeoPointResponse> getGeoFromAddress(@RequestParam String address) {
        return geocodingService.getGeoFromAddress(address);
    }
}