package com.ssafy.bango.domain.rentalhouse.service;

import com.ssafy.bango.domain.dongcode.dao.DongCodeDAO;
import com.ssafy.bango.domain.rentalhouse.dao.RentalHouseDAO;
import com.ssafy.bango.domain.rentalhouse.dto.RentalHouse;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalHouseService {
    private final RentalHouseDAO rentalHouseDAO;
    private final DongCodeDAO dongCodeDAO;

    public List<RentalHouse> getRentalHouses() {
        return rentalHouseDAO.findAll();
    }

    public List<RentalHouse> getRentalHousesByRegion(String dongName) {
        String dongCode = dongCodeDAO.findDongCodeByDongName(dongName);
        if (dongCode == null || dongCode.length() != 10) {
            return List.of();
        }

        return rentalHouseDAO.findByPnuStartingWith(dongCode).stream()
            .filter(rentalHouse -> {
                String pnu = rentalHouse.getPnu();
                return pnu != null && pnu.length() >= 10 && pnu.substring(0, 10).equals(dongCode);
            })
            .collect(Collectors.toList());
    }
}
