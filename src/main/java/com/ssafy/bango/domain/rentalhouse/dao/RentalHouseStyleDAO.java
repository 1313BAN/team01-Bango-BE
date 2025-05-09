package com.ssafy.bango.domain.rentalhouse.dao;

import com.ssafy.bango.domain.rentalhouse.dto.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.dto.RentalHouseStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalHouseStyleDAO extends JpaRepository<RentalHouseStyle, String> {
}
