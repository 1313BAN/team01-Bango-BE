package com.ssafy.bango.domain.dongcode.service;

import com.ssafy.bango.domain.dongcode.dao.DongCodeDAO;
import com.ssafy.bango.domain.dongcode.dto.DongCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DongCodeService {
    private final DongCodeDAO dongCodeDAO;

    public List<String> getDistinctSido() {
        return dongCodeDAO.getDistinctSidoNames();
    }

    public List<String> getDistinctGugun(String sidoName) {
        return dongCodeDAO.getDistinctGugunBySidoName(sidoName);
    }

    public List<DongCode> getDongCode(String sidoName, String gugunName) {
        return dongCodeDAO.findBySidoNameAndGugunNameAndDongNameIsNotNullOrderByDongName(sidoName, gugunName);
    }

}
