package com.ssafy.bango.domain.rentalnotice.feign;

import com.ssafy.bango.domain.rentalnotice.feign.dto.response.RentalNoticeApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalNoticeApiService {
    @Value("${DATA_GO_API_KEY}")
    private String DATA_GO_API_KEY;

    private final RentalNoticeApiClient rentalNoticeApiClient;

    public RentalNoticeApiResponse getRentalNoticeList() {
        return rentalNoticeApiClient.getRentalNoticeList(
                DATA_GO_API_KEY,
                "11", 1, 10
        );
    }
}
