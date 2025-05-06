package com.ssafy.ssafyhome.domain.member.dao;

import com.ssafy.ssafyhome.domain.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
    MemberDTO findById(String id);
    int insertMember(MemberDTO member);
    int updateMember(MemberDTO member);
}
