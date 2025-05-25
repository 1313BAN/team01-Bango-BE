package com.ssafy.bango.domain.rentalhouse.service;

import com.ssafy.bango.domain.rentalhouse.dto.request.RentalHouseSummaryRequest;
import com.ssafy.bango.domain.rentalhouse.dto.response.DongSummaryResponse;
import com.ssafy.bango.domain.rentalhouse.dto.response.GugunSummaryResponse;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouseSummary;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseSummaryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        rentalHouseSummaryRepository.saveAll(summaries);
    }

    public List<GugunSummaryResponse> getGugunSummary() {
        return rentalHouseSummaryRepository.getGugunSummary();
    }
    public List<DongSummaryResponse> getDongSummary() {
        return rentalHouseSummaryRepository.getDongSummary();
    }
}
