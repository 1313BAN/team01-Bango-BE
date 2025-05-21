package com.ssafy.bango.domain.rentalnotice.dto;

import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;

import java.util.List;

public record NoticeListResponseWithLiked(int pageNo, int pageCount, List<NoticeWithLiked> noticeWithLikeds) {

    public static NoticeListResponseWithLiked of(int pageNo, int pageCount, List<NoticeWithLiked> noticeWithLikeds) {
        return new NoticeListResponseWithLiked(pageNo, pageCount, noticeWithLikeds);
    }
}
