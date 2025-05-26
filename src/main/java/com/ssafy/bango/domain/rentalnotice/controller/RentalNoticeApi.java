package com.ssafy.bango.domain.rentalnotice.controller;

import com.ssafy.bango.domain.rentalnotice.dto.NoticeListResponseWithLiked;
import com.ssafy.bango.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

@Tag(name = "임대 주택 공고 컨트롤러", description = "임대 주택 공고 관련 API입니다.")
public interface RentalNoticeApi {
    @Operation(summary = "dummy 임대 주택 공고 쌓기", description = "dummy 임대 주택 공고 데이터를 DB에 적재하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "dummy 임대 주택 공고 쌓기 완료")
    })
    void getRentalListTest();

    @Operation(summary = "공고 조회", description = "로그인 되지 않은 경우(/) 공고 목록을 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "공고 목록 조회 완료")
    })
    ResponseEntity<ApiResponse<NoticeListResponseWithLiked>> getRentalListWithLike(int pageNo, int pageSize, Principal principal);
}
