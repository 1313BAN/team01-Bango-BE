package com.ssafy.bango.global.batch.chunk.rentalhouse;

import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouseStyle;
import com.ssafy.bango.domain.rentalhouse.service.KakaoGeocodingService;
import com.ssafy.bango.global.batch.dto.RentalHouseApiResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class RentalHouseApiItemProcessor implements ItemProcessor<List<RentalHouseApiResponse>, List<RentalHouse>> {
    private final KakaoGeocodingService geocodingService;

    @Override
    public List<RentalHouse> process(List<RentalHouseApiResponse> apiResponseList) {
        Map<String, RentalHouse> houseMap = new HashMap<>();
        log.info(">>> processing {} items", apiResponseList.size());
        long start = System.currentTimeMillis();

        makeRentalHouseAndRentalHouseStyle(apiResponseList, houseMap);

        long end = System.currentTimeMillis();
        log.warn(">>>>> Mapper Duration: {} ms", end - start);
        return new ArrayList<>(houseMap.values());
    }

    private void makeRentalHouseAndRentalHouseStyle(List<RentalHouseApiResponse> apiResponseList, Map<String, RentalHouse> houseMap) {
        for (RentalHouseApiResponse dto : apiResponseList) {
            String pnu = dto.getPnu();

            RentalHouse house = houseMap.get(pnu);
            if (house == null) {
                var geo = geocodingService.getGeoFromAddress(pnu, dto.getRnAdres()).orElse(null);
                house = RentalHouse.from(dto, geo);
                houseMap.put(pnu, house);
            }
            addStyleToExistingHouse(dto, house);
        }
    }

    private void addStyleToExistingHouse(RentalHouseApiResponse dto, RentalHouse existingHouse) {
        RentalHouseStyle style = RentalHouseStyle.from(dto, existingHouse);
        existingHouse.getStyles().add(style);
    }
}