package com.ssafy.bango.domain.noticelike.controller;

import com.ssafy.bango.domain.noticelike.dto.response.LikedNoticeResponse;
import com.ssafy.bango.domain.noticelike.entity.NoticeLike;
import com.ssafy.bango.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface NoticeLikeApi {
    ResponseEntity<ApiResponse<NoticeLike>> addLikedNotice(Integer noticeId, Principal principal);

    ResponseEntity<ApiResponse<?>> deleteLikedNotice(Integer noticeId, Principal principal);

    ResponseEntity<ApiResponse<LikedNoticeResponse>> getLikedNoticeList(Principal principal);
}
