package com.ssafy.bango.domain.rentalhouse.service;

import com.ssafy.bango.domain.rentalhouse.dao.RentalHouseDAO;
import com.ssafy.bango.domain.rentalhouse.dto.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.dto.request.DongCodeRequest;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalHouseService {
    private final RentalHouseDAO rentalHouseDAO;

    public List<RentalHouse> getRentalHouses() {
        return rentalHouseDAO.findAllWithFetch();
    }

    public List<RentalHouse> getRentalHousesByRegion(DongCodeRequest request) {
        String dongCode = request.getDongCode();

        return rentalHouseDAO.findByPnuStartingWith(dongCode).stream()
            .filter(rentalHouse -> {
                String pnu = rentalHouse.getPnu();
                return pnu != null && pnu.length() >= 10 && pnu.substring(0, 10).equals(dongCode);
            })
            .collect(Collectors.toList());
    }
}
