package com.ssafy.bango.global.util;

import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public class RentalNoticeSpecifications {
    public static Specification<RentalNotice> byStatusPeriod(String status) {
        return (root, query, cb) -> {
            LocalDate today = LocalDate.now();

            return switch (status.toLowerCase()) {
                case "모집중" -> cb.and(
                    cb.lessThanOrEqualTo(root.get("beginDate"), today),
                    cb.greaterThanOrEqualTo(root.get("endDate"), today)
                );
                case "모집예정" -> cb.greaterThan(root.get("beginDate"), today);
                case "모집완료" -> cb.lessThan(root.get("endDate"), today);
                default -> null; // 필터링 안 함
            };
        };
    }

    public static Specification<RentalNotice> bySupplyType(String supplyType) {
        return (root, query, cb) -> cb.equal(root.get("supplyType"), supplyType);
    }
}