package com.ssafy.bango.domain.rentalhouse.repository;

import com.ssafy.bango.domain.rentalhouse.dto.request.RentalHouseSummaryRequest;
import com.ssafy.bango.domain.rentalhouse.dto.response.DongSummaryResponse;
import com.ssafy.bango.domain.rentalhouse.dto.response.GugunSummaryResponse;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouseSummary;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalHouseSummaryRepository extends JpaRepository<RentalHouseSummary, Long> {
    void deleteAll();

    @Query("""
        SELECT new com.ssafy.bango.domain.rentalhouse.dto.request.RentalHouseSummaryRequest(
            r.pnuGugunCode, r.gugunName, r.pnuDongCode, d.dongName, COUNT(r), AVG(r.latitude), AVG(r.longitude)
        )
        FROM RentalHouse r
        JOIN DongCode d ON r.pnuDongCode = d.dongCode
        GROUP BY r.pnuGugunCode, r.pnuDongCode, r.gugunName, d.dongName
    """)
    List<RentalHouseSummaryRequest> getRentalHouseSummary();

    @Query("""
        SELECT new com.ssafy.bango.domain.rentalhouse.dto.response.DongSummaryResponse(
            r.dongCode, r.dongName, r.rentalCount, r.avgLatitude, r.avgLongitude
        )
        FROM RentalHouseSummary r
    """)
    List<DongSummaryResponse> getDongSummary();

    @Query("""
        SELECT new com.ssafy.bango.domain.rentalhouse.dto.response.GugunSummaryResponse(
            r.gugunCode, r.gugunName, SUM(r.rentalCount), AVG(r.avgLatitude), AVG(r.avgLongitude)
        )
        FROM RentalHouseSummary r
        GROUP BY r.gugunCode, r.gugunName
    """)
    List<GugunSummaryResponse> getGugunSummary();
}