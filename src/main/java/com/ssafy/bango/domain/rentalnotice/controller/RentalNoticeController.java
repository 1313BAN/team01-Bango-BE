package com.ssafy.bango.domain.rentalnotice.controller;

import com.ssafy.bango.domain.rentalnotice.dto.NoticeListResponseWithLiked;
import com.ssafy.bango.domain.rentalnotice.dto.NoticeWithLiked;
import com.ssafy.bango.domain.rentalnotice.service.RentalNoticeService;
import com.ssafy.bango.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.ssafy.bango.global.exception.enums.SuccessType.GET_RENTALNOTICE_LIST_SUCCESS;
import static com.ssafy.bango.global.exception.enums.SuccessType.GET_RENTALNOTICE_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class RentalNoticeController implements RentalNoticeApi {
    private final RentalNoticeService rentalNoticeService;

    @GetMapping("/dump")
    public void getRentalListTest() {
        rentalNoticeService.dumpRentalNoticeTable();
    }


    /**
     * 로그인된 경우 "/like"
     * 로그인 되지 않은 경우 ""
     */
    @GetMapping(value = {"", "/like"})
    public ResponseEntity<ApiResponse<NoticeListResponseWithLiked>> getRentalListWithLike(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                GET_RENTALNOTICE_LIST_SUCCESS,
                rentalNoticeService.getNoticeListWithLiked(pageNo, pageSize, principal)
        ));
    }

    /**
     * 로그인된 경우 "/like"
     * 로그인 되지 않은 경우 ""
     */
    @GetMapping(value = {"/{noticeId}", "/like/{noticeId}"})
    public ResponseEntity<ApiResponse<NoticeWithLiked>> getRentalWithLike(@PathVariable int noticeId, Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(
            GET_RENTALNOTICE_SUCCESS,
            rentalNoticeService.getNoticeWithLiked(noticeId, principal)
        ));
    }
}
