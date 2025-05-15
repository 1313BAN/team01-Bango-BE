package com.ssafy.bango.global.batch.chunk;

import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.service.KakaoGeocodingService;
import com.ssafy.bango.global.batch.dto.CustomRentalHouseMap;
import com.ssafy.bango.global.batch.dto.RentalHouseApiResponse;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class OpenApiItemProcessor implements ItemProcessor<List<RentalHouseApiResponse>, List<RentalHouse>> {
    private final ModelMapper modelMapper = new ModelMapper();
    private final KakaoGeocodingService geocodingService;

    @PostConstruct
    public void init() {
        modelMapper.addMappings(new CustomRentalHouseMap(geocodingService));
    }

    @Override
    public List<RentalHouse> process(@NonNull List<RentalHouseApiResponse> apiResponseList) {
        log.info(">>> processing item: {}", apiResponseList);
        log.info(">>> processing item list, size: {}", apiResponseList.size());

//        return apiResponseList.stream()
//            .map(apiResponse -> modelMapper.map(apiResponse, RentalHouse.class))
//            .collect(Collectors.toList());


        return apiResponseList.stream()
            .map(dto -> RentalHouse.from(dto, geocodingService.getGeoFromAddress(dto.getRnAdres()).orElse(null)))
            .collect(Collectors.toList());
    }
}