package com.ssafy.bango.global.common.health;

import com.ssafy.bango.domain.rentalhouse.dto.response.GeoPointResponse;
import com.ssafy.bango.domain.rentalhouse.service.KakaoGeocodingService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Profile("local")
public class GeocodingController {
    private final KakaoGeocodingService geocodingService;

    @GetMapping("/geocoding")
    public ResponseEntity<?> getGeoFromAddress(@RequestParam String address) {
        Optional<GeoPointResponse> geo = geocodingService.getGeoFromAddress(address);
        return geo.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.noContent().build());
    }
}