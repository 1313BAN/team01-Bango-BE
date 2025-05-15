package com.ssafy.bango.domain.rentalnotice.controller;

import com.ssafy.bango.domain.rentalnotice.dto.NoticeListResponse;
import com.ssafy.bango.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface RentalNoticeApi {
    void getRentalListTest();

    ResponseEntity<ApiResponse<NoticeListResponse>> getRentalNoticeList(int pageNo, int pageSize);
}
