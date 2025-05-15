package com.ssafy.bango.domain.noticelike.service;

import com.ssafy.bango.domain.member.entity.Member;
import com.ssafy.bango.domain.member.repository.MemberRepository;
import com.ssafy.bango.domain.noticelike.dto.response.LikedNoticeResponse;
import com.ssafy.bango.domain.noticelike.entity.NoticeLike;
import com.ssafy.bango.domain.noticelike.repository.NoticeLikeRepository;
import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import com.ssafy.bango.domain.rentalnotice.repository.RentalNoticeRepository;
import com.ssafy.bango.global.auth.jwt.JwtProvider;
import com.ssafy.bango.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

import static com.ssafy.bango.global.exception.enums.ErrorType.NOT_FOUND_MEMBER_ERROR;
import static com.ssafy.bango.global.exception.enums.ErrorType.NOT_FOUND_NOTICE_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeLikeService {
    private final NoticeLikeRepository noticeLikeRepository;
    private final MemberRepository memberRepository;
    private final RentalNoticeRepository rentalNoticeRepository;


    @Transactional
    public NoticeLike addLikedNotice(Integer noticeId, Principal principal) {
        Long memberId = JwtProvider.getMemberIdFromPrincipal(principal);

        Member member = memberRepository.getMemberByMemberId(memberId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER_ERROR));
        RentalNotice rentalNotice = rentalNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_NOTICE_ERROR));

        return noticeLikeRepository.getNoticeLikeByMemberAndRentalNotice(member, rentalNotice)
                .orElseGet(() -> {
                    NoticeLike noticeLike = NoticeLike.of(member, rentalNotice);
                    return noticeLikeRepository.save(noticeLike);
                });
    }

    @Transactional
    public void deleteLikedNotice(Integer noticeId, Principal principal) {
        Long memberId = JwtProvider.getMemberIdFromPrincipal(principal);

        Member member = memberRepository.getMemberByMemberId(memberId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER_ERROR));
        RentalNotice rentalNotice = rentalNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_NOTICE_ERROR));

        noticeLikeRepository.deleteByMemberAndRentalNotice(member, rentalNotice);
    }

    public LikedNoticeResponse getLikedNoticeList(Principal principal) {
        Long memberId = JwtProvider.getMemberIdFromPrincipal(principal);

        Member member = memberRepository.getMemberByMemberId(memberId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER_ERROR));

        List<NoticeLike> noticeLikeList = noticeLikeRepository.getNoticeLikesByMember(member);
        return LikedNoticeResponse.of(
                noticeLikeList.size(),
                noticeLikeList
        );
    }
}
