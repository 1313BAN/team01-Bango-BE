package com.ssafy.bango.domain.rentalhouse.controller;

import com.ssafy.bango.domain.rentalhouse.dto.request.FacilityRequest;
import com.ssafy.bango.domain.rentalhouse.dto.response.GeoPointResponse;
import com.ssafy.bango.domain.rentalhouse.service.KakaoFacilityService;
import com.ssafy.bango.domain.rentalhouse.service.KakaoGeocodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final KakaoGeocodingService geocodingService;
    private final KakaoFacilityService facilityService;

//    @GetMapping("/fetch-and-save")
//    public String fetchAndSaveRentalHouses() {
//        rentalHouseApiService.fetchAndSaveFromOpenApi();
//        return "success";
//    }

    @GetMapping("/geocoding")
    public Optional<GeoPointResponse> getGeoFromAddress(@RequestParam String address) {
        return geocodingService.getGeoFromAddress(address);
    }

    @GetMapping("/facilities/nearby")
    public void saveClosestFacilities(@ModelAttribute FacilityRequest request) {
        facilityService.saveClosestFacilities(request);
    }
}