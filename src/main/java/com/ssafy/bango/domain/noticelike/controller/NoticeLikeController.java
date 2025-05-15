package com.ssafy.bango.domain.noticelike.controller;

import com.ssafy.bango.domain.noticelike.entity.NoticeLike;
import com.ssafy.bango.domain.noticelike.service.NoticeLikeService;
import com.ssafy.bango.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.ssafy.bango.global.exception.enums.SuccessType.ADD_LIKED_NOTICE_CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/noticelike")
public class NoticeLikeController implements NoticeLikeApi {
    private final NoticeLikeService noticeLikeService;

    @PostMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeLike>> addLikedNotice(@PathVariable Integer noticeId, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        ADD_LIKED_NOTICE_CREATED,
                        noticeLikeService.addLikedNotice(noticeId, principal)
                ));
    }
}
