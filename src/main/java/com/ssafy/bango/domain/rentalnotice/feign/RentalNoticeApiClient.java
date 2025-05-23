package com.ssafy.bango.domain.rentalnotice.feign;

import com.ssafy.bango.domain.rentalnotice.feign.dto.response.RentalNoticeApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="RentalNoticeApiClient", url = "https://apis.data.go.kr/1613000")
public interface RentalNoticeApiClient {

    @GetMapping("/HWSPR02/rsdtRcritNtcList")
    RentalNoticeApiResponse getRentalNoticeList(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("brtcCode") String brtcCode, // 시도 코드
            @RequestParam("pageNo") int pageNo,
            @RequestParam("numOfRows") int numOfRows
    );
}
