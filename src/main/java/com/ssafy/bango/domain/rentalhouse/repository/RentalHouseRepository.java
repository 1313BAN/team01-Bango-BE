package com.ssafy.bango.domain.rentalhouse.repository;

import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalHouseRepository extends JpaRepository<RentalHouse, Integer> {
    @Modifying
    @Query("DELETE FROM RentalHouse")
    void deleteAll();

    @Query("SELECT h FROM RentalHouse h LEFT JOIN FETCH h.styles")
    List<RentalHouse> findAllWithFetch();

    // PNU의 첫 10자리로 매물 조회
    List<RentalHouse> findByPnuStartingWith(String pnuPrefix);

    @Query("SELECT r FROM RentalHouse r LEFT JOIN FETCH r.styles WHERE r.houseId = :houseId")
    Optional<RentalHouse> findByHouseIdWithStyles(int houseId);

    Optional<RentalHouse> findByHouseId(int houseId);

    List<RentalHouse> findByLatitudeIsNotNullAndLongitudeIsNotNull();

    @Query("""
        SELECT r FROM RentalHouse r
        WHERE r.latitude BETWEEN :minLatitude AND :maxLatitude
        AND r.longitude BETWEEN :minLongitude AND :maxLongitude
    """)
    List<RentalHouse> findByLatitudeAndLongitude(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude);
}