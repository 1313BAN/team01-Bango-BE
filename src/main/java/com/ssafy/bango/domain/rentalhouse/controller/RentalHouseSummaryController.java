package com.ssafy.bango.domain.rentalhouse.controller;

import static com.ssafy.bango.global.exception.enums.SuccessType.GET_DONG_SUMMARY_SUCCESS;
import static com.ssafy.bango.global.exception.enums.SuccessType.GET_GUGUN_SUMMARY_SUCCESS;

import com.ssafy.bango.domain.rentalhouse.dto.response.DongSummaryResponse;
import com.ssafy.bango.domain.rentalhouse.dto.response.GugunSummaryResponse;
import com.ssafy.bango.domain.rentalhouse.service.RentalHouseSummaryService;
import com.ssafy.bango.global.common.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rental")
@RequiredArgsConstructor
public class RentalHouseSummaryController implements RentalHouseSummaryApi {
    private final RentalHouseSummaryService rentalHouseSummaryService;

    @GetMapping("/gugun")
    public ApiResponse<List<GugunSummaryResponse>> getGugunSummary() {
        return ApiResponse.success(GET_GUGUN_SUMMARY_SUCCESS, rentalHouseSummaryService.getGugunSummary());
    }

    @GetMapping("/dong")
    public ApiResponse<List<DongSummaryResponse>> getDongSummary() {
        return ApiResponse.success(GET_DONG_SUMMARY_SUCCESS, rentalHouseSummaryService.getDongSummary());
    }
}