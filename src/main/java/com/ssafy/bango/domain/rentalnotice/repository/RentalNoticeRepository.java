package com.ssafy.bango.domain.rentalnotice.repository;

import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalNoticeRepository extends JpaRepository<RentalNotice, Integer> {
    List<RentalNotice> findAll();
}
