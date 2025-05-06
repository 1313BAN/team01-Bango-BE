package com.ssafy.ssafyhome.domain.dongcode.dao;

import com.ssafy.ssafyhome.domain.dongcode.dto.DongCodeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DongCodeDAO {
    List<DongCodeDTO> getDistinctSido();
    List<DongCodeDTO> getDistinctGugun(String sidoName);
    List<DongCodeDTO> getDongCode(String sidoName, String gugunName);
}
