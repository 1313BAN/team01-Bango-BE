package com.ssafy.bango.global.batch.chunk;

import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.service.KakaoGeocodingService;
import com.ssafy.bango.global.batch.dto.RentalHouseApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class OpenApiItemProcessor implements ItemProcessor<List<RentalHouseApiResponse>, List<RentalHouse>> {
    //    private final ModelMapper modelMapper = new ModelMapper();
    private final KakaoGeocodingService geocodingService;

//    @PostConstruct
//    public void init() {
//        modelMapper.addMappings(new CustomRentalHouseMap(geocodingService));
//    }


    @Override
    public List<RentalHouse> process(List<RentalHouseApiResponse> apiResponseList) {
        log.info(">>> processing {} items", apiResponseList.size());
        long start = System.currentTimeMillis();

        List<RentalHouse> result = apiResponseList.stream()
                .map(dto -> {
                    // dto.getPnu() 와 dto.getRnAdres() 를 함께 넘겨,
                    // PNU 단위로 캐싱된 geo를 먼저 찾고, 없으면 API 호출
                    var geoOpt = geocodingService.getGeoFromAddress(
                            dto.getPnu(), dto.getRnAdres()
                    );
                    return RentalHouse.from(dto, geoOpt.orElse(null));
                })
                .toList();

        long end = System.currentTimeMillis();
        log.warn(">>>>> Mapper Duration: {} ms", end - start);
        return result;
    }


//    @Override
//    public List<RentalHouse> process(@NonNull List<RentalHouseApiResponse> apiResponseList) {
////        log.info(">>> processing item: {}", apiResponseList);
//        log.info(">>> processing item list, size: {}", apiResponseList.size());
//
////        return apiResponseList.stream()
////            .map(apiResponse -> modelMapper.map(apiResponse, RentalHouse.class))
////            .collect(Collectors.toList());
//
//        long start = System.currentTimeMillis();
//
//
//        List<RentalHouse> ret = apiResponseList.stream()
//            .map(dto -> RentalHouse.from(dto, geocodingService.getGeoFromAddress(dto.getPnu(), dto.getRnAdres()).orElse(null)))
//            .collect(Collectors.toList());
//
//        long end = System.currentTimeMillis();
//        log.warn(">>>>> Mapper Duration: {}ms", start - end);
//
//        return ret;
//    }
}