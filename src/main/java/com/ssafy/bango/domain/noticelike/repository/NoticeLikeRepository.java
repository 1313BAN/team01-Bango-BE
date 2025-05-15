package com.ssafy.bango.domain.noticelike.repository;

import com.ssafy.bango.domain.member.entity.Member;
import com.ssafy.bango.domain.noticelike.entity.NoticeLike;
import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeLikeRepository extends JpaRepository<NoticeLike, Integer> {
    Optional<NoticeLike> getNoticeLikeByMemberAndRentalNotice(Member member, RentalNotice rentalNotice);
}
