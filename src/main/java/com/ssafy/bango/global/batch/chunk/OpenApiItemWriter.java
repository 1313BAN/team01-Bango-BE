package com.ssafy.bango.global.batch.chunk;

import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class OpenApiItemWriter implements ItemWriter<List<RentalHouse>> {
    private final RentalHouseRepository rentalHouseRepository;

    @Override
    public void write(Chunk<? extends List<RentalHouse>> chunk) {
        log.info(">>> write called");

        for (List<RentalHouse> rentalHouses : chunk) {
            log.info(">>>> write save rentalHouses");
            rentalHouseRepository.saveAllAndFlush(rentalHouses);
        }
    }
}
