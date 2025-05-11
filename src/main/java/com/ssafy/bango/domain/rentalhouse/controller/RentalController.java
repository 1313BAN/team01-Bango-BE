package com.ssafy.bango.domain.rentalhouse.controller;

import com.ssafy.bango.domain.rentalhouse.dto.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.service.RentalHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rental")
@RequiredArgsConstructor
public class RentalController {
    private final RentalHouseService rentalHouseService;

    @GetMapping()
    public ResponseEntity<List<RentalHouse>> getRentalHouses() {
        return ResponseEntity.ok(rentalHouseService.getRentalHouses());
    }

    @GetMapping("/by-region")
    public ResponseEntity<List<RentalHouse>> getRentalHousesByRegion(@RequestParam String region) {
        return ResponseEntity.ok(rentalHouseService.getRentalHousesByRegion(region));
    }
}