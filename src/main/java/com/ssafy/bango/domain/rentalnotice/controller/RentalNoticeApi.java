package com.ssafy.bango.domain.rentalnotice.controller;

import com.ssafy.bango.domain.rentalnotice.dto.NoticeListResponseWithLiked;
import com.ssafy.bango.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface RentalNoticeApi {
    void getRentalListTest();

    ResponseEntity<ApiResponse<NoticeListResponseWithLiked>> getRentalListWithLike(int pageNo, int pageSize, Principal principal);
}
