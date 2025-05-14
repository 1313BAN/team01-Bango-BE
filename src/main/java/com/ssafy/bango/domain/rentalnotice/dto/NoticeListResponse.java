package com.ssafy.bango.domain.rentalnotice.dto;

import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;

import java.util.List;

public record NoticeListResponse(int totalCount, List<RentalNotice> rentalNoticeList) {

    public static NoticeListResponse of(int totalCount, List<RentalNotice> rentalNoticeList) {
        return new NoticeListResponse(totalCount, rentalNoticeList);
    }
}
