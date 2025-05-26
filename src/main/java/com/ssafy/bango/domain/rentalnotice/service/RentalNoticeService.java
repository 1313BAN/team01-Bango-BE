package com.ssafy.bango.domain.rentalnotice.service;

import com.ssafy.bango.domain.noticelike.repository.NoticeLikeRepository;
import com.ssafy.bango.domain.rentalnotice.dto.NoticeListResponseWithLiked;
import com.ssafy.bango.domain.rentalnotice.dto.NoticeWithLiked;
import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import com.ssafy.bango.domain.rentalnotice.feign.RentalNoticeApiService;
import com.ssafy.bango.domain.rentalnotice.repository.RentalNoticeRepository;
import com.ssafy.bango.global.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalNoticeService {
    private final RentalNoticeRepository rentalNoticeRepository;
    private final NoticeLikeRepository noticeLikeRepository;

    private final RentalNoticeApiService rentalNoticeApiService;


    public NoticeListResponseWithLiked getNoticeListWithLiked(int pageNo, int pageSize, Principal principal) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        // 와 이거 너무 맘에 안들어요


        long memberId;
        Set<Integer> noticeLikeSet;
        if (!isNull(principal)) {
            memberId = JwtProvider.getMemberIdFromPrincipal(principal);
            noticeLikeSet = new HashSet<>(noticeLikeRepository.findLikedNoticeIdsByMemberId(memberId));
        } else noticeLikeSet = new HashSet<>();


        Page<RentalNotice> noticeList = rentalNoticeRepository.findAll(pageable);

        List<NoticeWithLiked> result = noticeList.stream()
                .map(notice -> NoticeWithLiked.of(notice, noticeLikeSet.contains(notice.getNoticeId())))
                .toList();

        return NoticeListResponseWithLiked.of(
                noticeList.getTotalPages(),
                noticeList.getTotalElements(),
                pageNo,
                noticeList.getNumberOfElements(),
                result
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
