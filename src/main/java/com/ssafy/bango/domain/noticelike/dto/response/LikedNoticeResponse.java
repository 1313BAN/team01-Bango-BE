package com.ssafy.bango.domain.noticelike.dto.response;

import com.ssafy.bango.domain.noticelike.entity.NoticeLike;

import java.util.List;

public record LikedNoticeResponse(
        int countLiked,
        List<NoticeLike> noticeLikeList) {

    public static LikedNoticeResponse of(int countLiked, List<NoticeLike> noticeLikeList) {
        return new LikedNoticeResponse(countLiked, noticeLikeList);
    }
}
