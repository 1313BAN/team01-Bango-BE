package com.ssafy.bango.domain.rentalnotice.service;

import com.ssafy.bango.domain.noticelike.repository.NoticeLikeRepository;
import com.ssafy.bango.domain.rentalnotice.dto.NoticeListResponseWithLiked;
import com.ssafy.bango.domain.rentalnotice.dto.NoticeWithLiked;
import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import com.ssafy.bango.domain.rentalnotice.feign.RentalNoticeApiService;
import com.ssafy.bango.domain.rentalnotice.repository.RentalNoticeRepository;
import com.ssafy.bango.global.auth.jwt.JwtProvider;
import com.ssafy.bango.global.exception.CustomException;
import com.ssafy.bango.global.exception.enums.ErrorType;
import com.ssafy.bango.global.util.RentalNoticeSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.StringUtils;

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

    public NoticeWithLiked getNoticeWithLiked(int noticeId, Principal principal) {
        RentalNotice notice = getRentalNotice(noticeId);

        boolean isLiked = false;
        if (!isNull(principal)) {
            long memberId = JwtProvider.getMemberIdFromPrincipal(principal);
            isLiked = noticeLikeRepository.existsByMember_MemberIdAndRentalNotice_NoticeId(memberId, noticeId);
        }
        return NoticeWithLiked.of(notice, isLiked);
    }

    private RentalNotice getRentalNotice(int noticeId) {
        return rentalNoticeRepository.findById(noticeId)
        .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_NOTICE_ERROR));
    }

    public NoticeListResponseWithLiked searchNoticeListWithLiked(
        int pageNo, int pageSize, String status, String supplyType, Principal principal) {

        Specification<RentalNotice> spec = buildSpecification(status, supplyType);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<RentalNotice> page = rentalNoticeRepository.findAll(spec, pageable);

        Set<Integer> likedIds = getLikedNoticeIds(principal);
        List<NoticeWithLiked> notices = page.stream()
            .map(notice -> NoticeWithLiked.of(notice, likedIds.contains(notice.getNoticeId())))
            .toList();

        return NoticeListResponseWithLiked.of(
            page.getTotalPages(),
            page.getTotalElements(),
            pageNo,
            page.getNumberOfElements(),
            notices
        );
    }

    private Specification<RentalNotice> buildSpecification(String status, String supplyType) {
        Specification<RentalNotice> spec = Specification.where(null);

        if (StringUtils.hasText(status)) {
            spec = spec.and(RentalNoticeSpecifications.byStatusPeriod(status));
        }
        if (StringUtils.hasText(supplyType)) {
            spec = spec.and(RentalNoticeSpecifications.bySupplyType(supplyType));
        }
        return spec;
    }

    private Set<Integer> getLikedNoticeIds(Principal principal) {
        if (principal == null) return Set.of();

        long memberId = JwtProvider.getMemberIdFromPrincipal(principal);
        return new HashSet<>(noticeLikeRepository.findLikedNoticeIdsByMemberId(memberId));
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
