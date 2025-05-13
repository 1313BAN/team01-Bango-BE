package com.ssafy.bango.domain.rentalhouse.repository;

import com.ssafy.bango.domain.rentalhouse.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {
}
