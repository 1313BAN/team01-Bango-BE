package com.ssafy.bango.domain.rentalhouse.service;

import com.ssafy.bango.domain.rentalhouse.dto.request.RentalHouseSummaryRequest;
import com.ssafy.bango.domain.rentalhouse.dto.response.DongSummaryResponse;
import com.ssafy.bango.domain.rentalhouse.dto.response.GugunSummaryResponse;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouseSummary;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseSummaryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalHouseSummaryService {
    private final RentalHouseSummaryRepository rentalHouseSummaryRepository;

    @Transactional
    public void makeAndSaveSummary() {
        List<RentalHouseSummaryRequest> summaryData = rentalHouseSummaryRepository.getRentalHouseSummary();
        List<RentalHouseSummary> summaries = summaryData.stream()
            .map(dto -> RentalHouseSummary.from(
                dto.getGugunCode(),
                dto.getGugunName(),
                dto.getDongCode(),
                dto.getDongName(),
                dto.getRentalCount(),
                dto.getAvgLatitude(),
                dto.getAvgLongitude()
            ))
            .toList();
        log.info("저장할 summary 개수: {}", summaries.size());
        rentalHouseSummaryRepository.saveAll(summaries);
        rentalHouseSummaryRepository.flush(); // flush 명시적으로 호출해서 DB 반영 확인
    }

    public List<GugunSummaryResponse> getGugunSummary() {
        return rentalHouseSummaryRepository.getGugunSummary();
    }
    public List<DongSummaryResponse> getDongSummary() {
        return rentalHouseSummaryRepository.getDongSummary();
    }
}
