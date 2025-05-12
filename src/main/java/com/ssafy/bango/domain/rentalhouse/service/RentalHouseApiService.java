package com.ssafy.bango.domain.rentalhouse.service;

import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseRepository;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseStyleRepository;
import com.ssafy.bango.domain.rentalhouse.dto.response.GeoPointResponse;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.dto.response.RentalHouseApiResponse;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouseStyle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalHouseApiService {
    private final RestTemplate restTemplate;
    private final GeocodingService geocodingService;
    private final RentalHouseRepository rentalHouseRepository;
    private final RentalHouseStyleRepository rentalHouseStyleRepository;

    public void fetchAndSaveFromOpenApi() {
        String apiUrl = "https://apis.data.go.kr/1613000/HWSPR04/rentalHouseGwList?serviceKey=GajU3wBEQyXaoPxusoceynnphlBgHHAZCBIakU0o85ex4aPwIWocpTYVKb%2BRBerkKLPjsRHwLOVyGmQ%2F10QeJQ%3D%3D&brtcCode=11&signguCode=110&numOfRows=10&pageNo=1";

        ResponseEntity<RentalHouseApiResponse[]> response = restTemplate.getForEntity(apiUrl, RentalHouseApiResponse[].class);
        log.info("API response status: {}", response.getStatusCode());
        log.debug("API response body: {}", response.getBody());

        RentalHouseApiResponse[] rentalData = response.getBody();

        for (RentalHouseApiResponse data : rentalData) {
            Optional<GeoPointResponse> geo = geocodingService.getGeoFromAddress(data.getRnAdres());

            RentalHouse house = RentalHouse.builder()
                    .address(data.getRnAdres())
                    .pnu(data.getPnu())
                    .buildStyle(data.getStyleNm())
                    .hasElevator(data.getElvtrInstlAtNm())
                    .parkingCount(data.getParkngCo() + "")
                    .houseType(data.getHouseTyNm())
                    .gugunCode(Integer.parseInt(data.getBrtcCode()))
                    .gugunName(data.getBrtcNm())
                    .sidoCode(Integer.parseInt(data.getSignguCode()))
                    .sidoName(data.getSignguNm())
                    .supplyType(data.getSuplyTyNm())
                    .institudeType(data.getInsttNm())
                    .builtAt(parseDate(data.getCompetDe()))
                    .latitude(geo.map(GeoPointResponse::latitude).orElse(null))
                    .longitude(geo.map(GeoPointResponse::longitude).orElse(null))
                    .build();

            rentalHouseRepository.save(house);

            RentalHouseStyle style = RentalHouseStyle.builder()
                    .rentalHouse(house)
                    .styleName(data.getStyleNm())
                    .supplyPrivateArea(data.getSuplyCmnuseAr().floatValue())
                    .supplyPublicArea(data.getSuplyPrvuseAr().floatValue())
                    .baseDeposit(data.getBassRentGtn())
                    .baseMonthlyRent(data.getBassMtRntchrg())
                    .baseConversionDeposit(data.getBassCnvrsGtnLmt())
                    .build();

            rentalHouseStyleRepository.save(style);
        }
    }

    private LocalDateTime parseDate(String dateStr) {
        if (StringUtils.hasText(dateStr)) {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
        }
        return null;
    }
}
