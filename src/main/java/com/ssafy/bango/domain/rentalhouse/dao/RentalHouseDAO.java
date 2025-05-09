package com.ssafy.bango.domain.rentalhouse.dao;

import com.ssafy.bango.domain.dongcode.dto.DongCode;
import com.ssafy.bango.domain.rentalhouse.dto.RentalHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalHouseDAO extends JpaRepository<RentalHouse, String> {
    List<RentalHouse> findAll();
//    List<RentalHouse> findByDongCode(String dongCode);
}
