package com.ssafy.bango.domain.member.dao;

import com.ssafy.bango.domain.member.dto.Member;

public interface MemberDAO {
    Member findById(String id);
    int insertMember(Member member);
    int updateMember(Member member);
}
