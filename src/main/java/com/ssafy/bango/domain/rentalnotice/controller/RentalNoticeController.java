package com.ssafy.bango.domain.rentalnotice.controller;

import com.ssafy.bango.domain.rentalnotice.dto.NoticeListResponseWithLiked;
import com.ssafy.bango.domain.rentalnotice.service.RentalNoticeService;
import com.ssafy.bango.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.bango.global.exception.enums.SuccessType.GET_RENTALNOTICE_LIST_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class RentalNoticeController implements RentalNoticeApi {
    private final RentalNoticeService rentalNoticeService;

    @GetMapping("/dump")
    public void getRentalListTest() {
        rentalNoticeService.dumpRentalNoticeTable();
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<NoticeListResponse>> getRentalNoticeList(
//            @RequestParam(defaultValue = "1") int pageNo,
//            @RequestParam(defaultValue = "10") int pageSize
//    ) {
//        return ResponseEntity.ok(ApiResponse.success(
//                GET_RENTALNOTICE_LIST_SUCCESS,
//                rentalNoticeService.getRentalNoticeList(pageNo, pageSize)
//        ));
//    }

    @GetMapping("/v2")
    public ResponseEntity<ApiResponse<NoticeListResponseWithLiked>> getRentalListWithLike(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestHeader(value = "Authorization", required = false) String accessToken
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                GET_RENTALNOTICE_LIST_SUCCESS,
                rentalNoticeService.getNoticeListWithLiked(pageNo, pageSize, accessToken)
        ));
    }
}
