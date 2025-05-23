package com.ssafy.bango.domain.rentalnotice.feign.dto.response;

import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class RentalNoticeApiResponse {
    private Response response;

    @Getter
    @ToString
    @NoArgsConstructor
    public static class Response {
        private Header header;
        private Body body;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class Body {
        private String totalCount;
        private String numOfRows;
        private String pageNo;
        private List<Item> item;
    }

    public List<RentalNotice> toEntityList() {
        List<Item> items = response.body.getItem();
        return items.stream()
                .map(Item::toEntity)
                .toList();
    }
}
