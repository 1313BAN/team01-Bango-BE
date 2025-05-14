package com.ssafy.bango.domain.rentalnotice.service;

import com.ssafy.bango.domain.rentalnotice.dto.NoticeListResponse;
import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import com.ssafy.bango.domain.rentalnotice.feign.RentalNoticeApiService;
import com.ssafy.bango.domain.rentalnotice.repository.RentalNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalNoticeService {
    private final RentalNoticeRepository rentalNoticeRepository;
    private final RentalNoticeApiService rentalNoticeApiService;

    public NoticeListResponse getRentalNoticeList() {
        List<RentalNotice> rentalNoticeList = rentalNoticeRepository.findAll();
        return NoticeListResponse.of(
                rentalNoticeList.size(),
                rentalNoticeList
        );
    }

    /**
     * 임시 메서드
     * 개발 과정에서 데이터 베이스 채우기 용
     */
    @Transactional
    public void dumpRentalNoticeTable() {
        List<RentalNotice> noticeList = rentalNoticeApiService
                .getRentalNoticeList()
                .toEntityList();

        rentalNoticeRepository.saveAll(noticeList);
    }

}
