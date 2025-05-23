package com.ssafy.bango.domain.noticelike.controller;

import com.ssafy.bango.domain.noticelike.dto.response.LikedNoticeResponse;
import com.ssafy.bango.domain.noticelike.entity.NoticeLike;
import com.ssafy.bango.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

@Tag(name = "공고 찜하기 컨트롤러", description = "공고 찜하기 관련 API입니다.")
public interface NoticeLikeApi {
    @Operation(summary = "공고 찜하기", description = "공고 찜하기 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "찜하기 완료")
    })
    ResponseEntity<ApiResponse<NoticeLike>> addLikedNotice(Integer noticeId, Principal principal);

    @Operation(summary = "공고 찜해제", description = "공고 찜해제 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "찜해제 완료")
    })
    ResponseEntity<ApiResponse<?>> deleteLikedNotice(Integer noticeId, Principal principal);

    @Operation(summary = "공고 찜한 목록 조회", description = "공고 찜한 목록 조회 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "공고 찜한 목록 조회 완료")
    })
    ResponseEntity<ApiResponse<LikedNoticeResponse>> getLikedNoticeList(Principal principal);
}
