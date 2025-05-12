package com.ssafy.bango.domain.dongcode.service;

import com.ssafy.bango.domain.dongcode.repository.DongCodeRepository;
import com.ssafy.bango.domain.dongcode.entity.DongCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DongCodeService {
    private final DongCodeRepository dongCodeRepository;

    public List<String> getDistinctSido() {
        return dongCodeRepository.getDistinctSidoNames();
    }

    public List<String> getDistinctGugun(String sidoName) {
        return dongCodeRepository.getDistinctGugunBySidoName(sidoName);
    }

    public List<DongCode> getDongCode(String sidoName, String gugunName) {
        return dongCodeRepository.findBySidoNameAndGugunNameAndDongNameIsNotNullOrderByDongName(sidoName, gugunName);
    }

}
