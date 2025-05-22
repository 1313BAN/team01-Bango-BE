package com.ssafy.bango.global.batch.chunk.facility;

import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;

@Slf4j
@StepScope
@RequiredArgsConstructor
public class FacilityApiItemReader implements ItemReader<RentalHouse> {
    private final RentalHouseRepository rentalHouseRepository;
    private List<RentalHouse> rentalHouseList;
    private int nextIndex = 0;

    @PostConstruct
    public void init() {
        rentalHouseList = rentalHouseRepository.findAll();
        log.info("총 {}건의 매물을 불러왔습니다.", rentalHouseList.size());
    }

    @Override
    public RentalHouse read() {
        while (nextIndex < rentalHouseList.size()) {
            RentalHouse rentalHouse = rentalHouseList.get(nextIndex);
            nextIndex++;
            if (rentalHouse.getLatitude() == null || rentalHouse.getLongitude() == null) {
                continue;
            }
            return rentalHouse;
        }
        return null; // 모든 데이터를 다 읽었을 때
    }
}