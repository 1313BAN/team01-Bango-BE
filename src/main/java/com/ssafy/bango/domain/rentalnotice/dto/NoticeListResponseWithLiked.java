package com.ssafy.bango.domain.rentalnotice.dto;

import java.util.List;

public record NoticeListResponseWithLiked(int totalPageNo, long totalPageCount, int pageNo, int pageCount, List<NoticeWithLiked> noticeWithLikeds) {

    public static NoticeListResponseWithLiked of(int totalPageNo, long totalPageCount, int pageNo, int pageCount, List<NoticeWithLiked> noticeWithLikeds) {
        return new NoticeListResponseWithLiked(totalPageNo, totalPageCount, pageNo, pageCount, noticeWithLikeds);
    }
}
