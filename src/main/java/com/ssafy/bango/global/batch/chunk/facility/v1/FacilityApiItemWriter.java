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
            log.info(">>>> write save facilities");
            facilityRepository.saveAllAndFlush(facilities);
        }
    }
}