package com.ssafy.bango.domain.rentalnotice.dto;

import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;

public record NoticeWithLiked(RentalNotice rentalNotice, boolean isLiked) {
    public static NoticeWithLiked of(RentalNotice rentalNotice, boolean isLiked) {
        return new NoticeWithLiked(rentalNotice, isLiked);
    }
}
