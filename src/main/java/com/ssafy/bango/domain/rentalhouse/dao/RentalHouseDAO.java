package com.ssafy.bango.domain.rentalhouse.dao;

import com.ssafy.bango.domain.rentalhouse.dto.RentalHouse;
import feign.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalHouseDAO extends JpaRepository<RentalHouse, String> {
    @Query("SELECT h FROM RentalHouse h LEFT JOIN FETCH h.styles")
    List<RentalHouse> findAllWithFetch();

    // PNU의 첫 10자리로 매물 조회
    List<RentalHouse> findByPnuStartingWith(String pnuPrefix);

    @Query("SELECT r FROM RentalHouse r LEFT JOIN FETCH r.styles WHERE r.houseId = :houseId")
    Optional<RentalHouse> findByHouseIdWithStyles(@Param("houseId") int houseId);
}
