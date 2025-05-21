package com.ssafy.bango.domain.rentalnotice.service;

import com.ssafy.bango.domain.noticelike.repository.NoticeLikeRepository;
import com.ssafy.bango.domain.rentalnotice.dto.NoticeListResponseWithLiked;
import com.ssafy.bango.domain.rentalnotice.dto.NoticeWithLiked;
import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import com.ssafy.bango.domain.rentalnotice.feign.RentalNoticeApiService;
import com.ssafy.bango.domain.rentalnotice.repository.RentalNoticeRepository;
import com.ssafy.bango.global.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalNoticeService {
    private final RentalNoticeRepository rentalNoticeRepository;
    private final NoticeLikeRepository noticeLikeRepository;

    private final JwtProvider jwtProvider;

    private final RentalNoticeApiService rentalNoticeApiService;

//    public NoticeListResponse getRentalNoticeList(int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
//        Page<RentalNotice> rentalNoticeList = rentalNoticeRepository.findAll(pageable);
//        return NoticeListResponse.of(
//                pageNo,
//                rentalNoticeList.getNumberOfElements(),
//                rentalNoticeList.toList()
//        );
//    }

    public NoticeListResponseWithLiked getNoticeListWithLiked(int pageNo, int pageSize, String accessToken) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        // 와 이거 너무 맘에 안들어요
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
            accessToken =  accessToken.substring("Bearer ".length());
        }

        long memberId;
//        log.warn("AccessToken : {}", accessToken);
        try {
            memberId = jwtProvider.getMemberIdFromJwt(accessToken);
        } catch (Exception e) {
            memberId = -1L;
        }

        Page<RentalNotice> noticeList = rentalNoticeRepository.findAll(pageable);
        Set<Integer> noticeLikeSet = new HashSet<>(noticeLikeRepository.findLikedNoticeIdsByMemberId(memberId));

//        log.warn("member: {} / set: {} ",memberId, noticeLikeSet);

        List<NoticeWithLiked> result = noticeList.stream()
                .map(notice -> NoticeWithLiked.of(notice, noticeLikeSet.contains(notice.getNoticeId())))
                .toList();


        return NoticeListResponseWithLiked.of(
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
