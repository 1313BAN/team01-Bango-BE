package com.ssafy.bango.domain.rentalnotice.controller;

import com.ssafy.bango.domain.rentalnotice.dto.NoticeListResponse;
import com.ssafy.bango.domain.rentalnotice.service.RentalNoticeService;
import com.ssafy.bango.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ssafy.bango.global.exception.enums.SuccessType.GET_RENTALNOTCIE_LIST_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class RentalNoticeController implements RentalNoticeApi {
    private final RentalNoticeService rentalNoticeService;

    @GetMapping("/dump")
    public void getRentalListTest() {
        rentalNoticeService.dumpRentalNoticeTable();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<NoticeListResponse>> getRentalNoticeList() {
        return ResponseEntity.ok(ApiResponse.success(
                GET_RENTALNOTCIE_LIST_SUCCESS,
                rentalNoticeService.getRentalNoticeList()
        ));
    }
}
