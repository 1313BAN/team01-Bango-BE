package com.ssafy.bango.domain.rentalhouse.service;

import static com.ssafy.bango.global.exception.enums.ErrorType.HOUSE_ID_NOT_FOUND;

import com.ssafy.bango.domain.rentalhouse.dto.request.LatLongBoundsRequest;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseRepository;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.dto.request.DongCodeRequest;
import com.ssafy.bango.global.exception.CustomException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalHouseService {
    private final RentalHouseRepository rentalHouseRepository;

    public List<RentalHouse> getRentalHouses() {
        return rentalHouseRepository.findAllWithFetch();
    }

    public List<RentalHouse> getRentalHousesByRegion(DongCodeRequest request) {
        String dongCode = request.getDongCode();

        return rentalHouseRepository.findByPnuStartingWith(dongCode).stream()
            .filter(rentalHouse -> {
                String pnu = rentalHouse.getPnu();
                return pnu != null && pnu.length() >= 10 && pnu.substring(0, 10).equals(dongCode);
            })
            .collect(Collectors.toList());
    }

    public RentalHouse getRentalHouseWithStyles(int houseId) {
        return rentalHouseRepository.findByHouseIdWithStyles(houseId)
            .orElseThrow(() -> new CustomException(HOUSE_ID_NOT_FOUND));
    }

    public RentalHouse getRentalHouseByHouseId(int houseId) {
        return rentalHouseRepository.findByHouseId(houseId)
            .orElseThrow(() -> new CustomException(HOUSE_ID_NOT_FOUND));
    }

    public List<RentalHouse> getRentalHousesByLatLongBounds(LatLongBoundsRequest request) {
        return rentalHouseRepository.findByLatitudeAndLongitude(request.getMinLatitude(), request.getMaxLatitude(), request.getMinLongitude(), request.getMaxLongitude());
    }
}
