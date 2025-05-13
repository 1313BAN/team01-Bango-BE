package com.ssafy.bango.domain.member.repository;

import com.ssafy.bango.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsBySocialId(String socialId);
    Optional<Member> getMemberBySocialId(String socialId);
}
