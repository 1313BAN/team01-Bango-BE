package com.ssafy.bango.domain.rentalhouse.repository;

import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalHouseRepository extends JpaRepository<RentalHouse, Integer> {
    @Query("SELECT h FROM RentalHouse h LEFT JOIN FETCH h.styles")
    List<RentalHouse> findAllWithFetch();

    // PNU의 첫 10자리로 매물 조회
    List<RentalHouse> findByPnuStartingWith(String pnuPrefix);

    @Query("SELECT r FROM RentalHouse r LEFT JOIN FETCH r.styles WHERE r.houseId = :houseId")
    Optional<RentalHouse> findByHouseIdWithStyles(int houseId);
}
