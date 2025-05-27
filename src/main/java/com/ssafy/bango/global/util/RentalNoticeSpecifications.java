package com.ssafy.bango.global.util;

import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public class RentalNoticeSpecifications {
    public static final String STATUS_RECRUITING = "모집중";
    public static final String STATUS_UPCOMING = "모집예정";
    public static final String STATUS_COMPLETED = "모집완료";

    public static Specification<RentalNotice> byStatusPeriod(String status) {
        return (root, query, cb) -> {
            if (status == null || status.trim().isEmpty()) return null; // 필터링 안 함

            LocalDate today = LocalDate.now();
            return switch (status.trim()) {
                case STATUS_RECRUITING -> cb.and(
                    cb.lessThanOrEqualTo(root.get("beginDate"), today),
                    cb.greaterThanOrEqualTo(root.get("endDate"), today)
                );
                case STATUS_UPCOMING -> cb.greaterThan(root.get("beginDate"), today);
                case STATUS_COMPLETED -> cb.lessThan(root.get("endDate"), today);
                default -> null; // 필터링 안 함
            };
        };
    }

    public static Specification<RentalNotice> bySupplyType(String supplyType) {
        return (root, query, cb) -> cb.equal(root.get("supplyType"), supplyType);
    }
}