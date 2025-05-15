package com.ssafy.bango.domain.noticelike.controller;

import com.ssafy.bango.domain.noticelike.dto.response.LikedNoticeResponse;
import com.ssafy.bango.domain.noticelike.entity.NoticeLike;
import com.ssafy.bango.domain.noticelike.service.NoticeLikeService;
import com.ssafy.bango.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.ssafy.bango.global.exception.enums.SuccessType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class NoticeLikeController implements NoticeLikeApi {
    private final NoticeLikeService noticeLikeService;

    @PostMapping("/{noticeId}/like")
    public ResponseEntity<ApiResponse<NoticeLike>> addLikedNotice(@PathVariable Integer noticeId, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        ADD_LIKED_NOTICE_CREATED,
                        noticeLikeService.addLikedNotice(noticeId, principal)
                ));
    }

    @DeleteMapping("/{noticeId}/like")
    public ResponseEntity<ApiResponse<?>> deleteLikedNotice(@PathVariable Integer noticeId, Principal principal) {

        noticeLikeService.deleteLikedNotice(noticeId, principal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success(DELETE_LIKED_NOTICE_SUCCESS));
    }

    @GetMapping("/like")
    public ResponseEntity<ApiResponse<LikedNoticeResponse>> getLikedNoticeList(Principal principal) {

        return ResponseEntity.ok(ApiResponse.success(
                GET_LIKED_NOTICE_SUCCESS,
                noticeLikeService.getLikedNoticeList(principal)
        ));
    }
}
