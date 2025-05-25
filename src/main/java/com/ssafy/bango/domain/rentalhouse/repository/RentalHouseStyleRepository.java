package com.ssafy.bango.domain.rentalhouse.repository;

import com.ssafy.bango.domain.rentalhouse.entity.RentalHouseStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalHouseStyleRepository extends JpaRepository<RentalHouseStyle, String> {
    @Modifying
    @Query("DELETE FROM RentalHouseStyle")
    void deleteAll();
}
