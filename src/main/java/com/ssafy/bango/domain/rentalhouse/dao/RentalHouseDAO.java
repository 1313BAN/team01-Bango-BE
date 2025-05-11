package com.ssafy.bango.domain.rentalhouse.dao;

import com.ssafy.bango.domain.rentalhouse.dto.RentalHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalHouseDAO extends JpaRepository<RentalHouse, String> {
    List<RentalHouse> findAll();

    // PNU의 첫 10자리로 매물 조회
    List<RentalHouse> findByPnuStartingWith(String pnuPrefix);
}
