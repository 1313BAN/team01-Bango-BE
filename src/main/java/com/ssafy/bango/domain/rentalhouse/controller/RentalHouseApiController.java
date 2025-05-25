package com.ssafy.bango.domain.rentalhouse.controller;

import com.ssafy.bango.domain.rentalhouse.dto.response.GeoPointResponse;
import com.ssafy.bango.domain.rentalhouse.service.KakaoGeocodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    private final KakaoGeocodingService geocodingService;

    @GetMapping("/geocoding")
    public ResponseEntity<?> getGeoFromAddress(@RequestParam String address) {
        Optional<GeoPointResponse> geo = geocodingService.getGeoFromAddress(address);
        return geo.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.noContent().build());
    }
}