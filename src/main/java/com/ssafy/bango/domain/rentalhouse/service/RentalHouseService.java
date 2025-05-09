package com.ssafy.bango.domain.rentalhouse.service;

import com.ssafy.bango.domain.rentalhouse.dao.RentalHouseDAO;
import com.ssafy.bango.domain.rentalhouse.dto.RentalHouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalHouseService {
    private final RentalHouseDAO rentalHouseDAO;

    public List<RentalHouse> getRentalHouses() {
        return rentalHouseDAO.findAll();
    }

//    public List<String> getRentalHousesByRegion(String dongName) {
//        return rentalHouseDAO.getRentalHousesByRegion(dongName);
//    }
}
