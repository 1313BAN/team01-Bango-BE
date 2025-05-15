package com.ssafy.bango.domain.rentalnotice.dto;

import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;

import java.util.List;

public record NoticeListResponse(int pageNo, int pageCount, List<RentalNotice> rentalNoticeList) {

    public static NoticeListResponse of(int pageNo, int pageCount, List<RentalNotice> rentalNoticeList) {
        return new NoticeListResponse(pageNo, pageCount, rentalNoticeList);
    }
}
