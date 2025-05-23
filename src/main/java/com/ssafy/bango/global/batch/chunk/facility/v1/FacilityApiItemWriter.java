package com.ssafy.bango.global.batch.chunk.facility.v1;

import com.ssafy.bango.domain.rentalhouse.entity.Facility;
import com.ssafy.bango.domain.rentalhouse.repository.FacilityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@Slf4j
@RequiredArgsConstructor
public class FacilityApiItemWriter implements ItemWriter<List<Facility>> {

    private final FacilityRepository facilityRepository;

    @Override
    public void write(Chunk<? extends List<Facility>> items) {
        log.info(">>> write called");
        for (List<Facility> facilities : items) {
            if (!facilities.isEmpty()) {
                try {
                    facilityRepository.saveAllAndFlush(facilities);
                    log.info("시설 정보 {} 건 저장 완료", facilities.size());
                } catch (Exception e) {
                    log.error("시설 정보 저장 중 오류 발생: {} 건", facilities.size(), e);
                    throw e; // Spring Batch가 처리할 수 있도록 예외를 다시 던짐
                }
            }
        }
    }
}