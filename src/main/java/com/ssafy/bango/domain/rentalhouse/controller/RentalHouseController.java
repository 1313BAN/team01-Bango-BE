package com.ssafy.bango.domain.rentalhouse.controller;

import static com.ssafy.bango.global.exception.enums.SuccessType.GET_RENTALHOUSES_BY_DONGCODE_SUCCESS;
import static com.ssafy.bango.global.exception.enums.SuccessType.GET_RENTALHOUSES_SUCCESS;
import static com.ssafy.bango.global.exception.enums.SuccessType.GET_RENTALHOUSE_SUCCESS;

import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.dto.request.DongCodeRequest;
import com.ssafy.bango.domain.rentalhouse.service.RentalHouseService;
import com.ssafy.bango.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rental")
@RequiredArgsConstructor
public class RentalHouseController implements RentalHouseApi {
    private final RentalHouseService rentalHouseService;

    @GetMapping()
    public ApiResponse<List<RentalHouse>> getRentalHouses() {
        return ApiResponse.success(GET_RENTALHOUSES_SUCCESS, rentalHouseService.getRentalHouses());
    }

    @GetMapping("/by-region")
    public ApiResponse<List<RentalHouse>> getRentalHousesByRegion(@Valid @ModelAttribute DongCodeRequest request) {
        return ApiResponse.success(GET_RENTALHOUSES_BY_DONGCODE_SUCCESS, rentalHouseService.getRentalHousesByRegion(request));
    }

    @GetMapping("/{houseId}")
    public ApiResponse<RentalHouse> getRentalHouse(@PathVariable int houseId) {
        return ApiResponse.success(GET_RENTALHOUSE_SUCCESS, rentalHouseService.getRentalHouseWithStyles(houseId));
    }
}