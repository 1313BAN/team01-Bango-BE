package com.ssafy.bango.global.batch.chunk.rentalhouse;

import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class RentalHouseApiItemWriter implements ItemWriter<List<RentalHouse>> {
    private final RentalHouseRepository rentalHouseRepository;

    @Override
    public void write(Chunk<? extends List<RentalHouse>> chunk) {
        log.info(">>> write called");
        long start = System.currentTimeMillis();

        for (List<RentalHouse> rentalHouses : chunk) {
            if (!rentalHouses.isEmpty()) {
                try {
                    rentalHouseRepository.saveAllAndFlush(rentalHouses);
                    log.info("주택 정보 {} 건 저장 완료", rentalHouses.size());
                } catch (Exception e) {
                    log.error("주택 정보 저장 중 오류 발생: {} 건", rentalHouses.size(), e);
                    throw e; // Spring Batch가 처리할 수 있도록 예외를 다시 던짐
                }
            }
        }
        long end = System.currentTimeMillis();
        log.warn(">>>>> Write Duration: {} ms", end - start);
    }
}
