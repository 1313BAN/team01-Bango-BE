package com.ssafy.ssafyhome.domain.dongcode.service;

import com.ssafy.ssafyhome.domain.dongcode.dao.DongCodeDAO;
import com.ssafy.ssafyhome.domain.dongcode.dto.DongCodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DongCodeService {
    private final DongCodeDAO dongCodeDAO;

    public List<DongCodeDTO> getDistinctSido() {
        return dongCodeDAO.getDistinctSido();
    }

    public List<DongCodeDTO> getDistinctGugun(String sidoName) {
        return dongCodeDAO.getDistinctGugun(sidoName);
    }

    public List<DongCodeDTO> getDongCode(String sidoName, String gugunName) {
        return dongCodeDAO.getDongCode(sidoName, gugunName);
    }

}
