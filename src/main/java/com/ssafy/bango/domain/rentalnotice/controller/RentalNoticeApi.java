package com.ssafy.bango.domain.rentalnotice.controller;

import com.ssafy.bango.domain.rentalnotice.dto.NoticeListResponseWithLiked;
import com.ssafy.bango.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

@Tag(name = "임대 주택 공고 컨트롤러", description = "임대 주택 공고 관련 API입니다.")
public interface RentalNoticeApi {
    @Operation(summary = "임대 주택 공고 조회", description = "임대 주택 공고 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "임대 주택 공고 조회 완료")
    })
    void getRentalListTest();

    @Operation(summary = "찜한 공고 주택 조회", description = "찜한 공고 주택을 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "찜한 공고 주택 조회 완료")
    })
    ResponseEntity<ApiResponse<NoticeListResponseWithLiked>> getRentalListWithLike(int pageNo, int pageSize, Principal principal);
}
