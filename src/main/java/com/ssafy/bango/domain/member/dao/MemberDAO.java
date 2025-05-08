package com.ssafy.bango.domain.member.dao;

import com.ssafy.bango.domain.member.dto.MemberDTO;

public interface MemberDAO {
    MemberDTO findById(String id);
    int insertMember(MemberDTO member);
    int updateMember(MemberDTO member);
}
