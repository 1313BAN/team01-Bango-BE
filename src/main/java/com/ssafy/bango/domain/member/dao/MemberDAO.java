package com.ssafy.bango.domain.member.dao;

import com.ssafy.bango.domain.member.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDAO extends JpaRepository<Member, Long> {
    boolean existsBySocialId(String socialId);
}
