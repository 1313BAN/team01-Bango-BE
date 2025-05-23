package com.ssafy.bango.domain.rentalnotice.repository;

import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalNoticeRepository extends JpaRepository<RentalNotice, Integer> {
    Page<RentalNotice> findAll(Pageable pageable);
}
