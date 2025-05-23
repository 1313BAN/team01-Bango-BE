package com.ssafy.bango.domain.ai.controller;

import com.ssafy.bango.domain.ai.service.AiChatService;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.service.RentalHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class AiController {
    private final AiChatService aiChatService;
    private final RentalHouseService rentalHouseService;

    @GetMapping("/rental/{houseId}/facilities")
    public Object getFacilityInfo(@PathVariable int houseId) {
        RentalHouse house = rentalHouseService.getRentalHouseByHouseId(houseId);
        return aiChatService.facilityChat(house.getAddress());
    }
}
