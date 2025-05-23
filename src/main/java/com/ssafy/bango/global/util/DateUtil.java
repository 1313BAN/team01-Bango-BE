package com.ssafy.bango.global.util;


import com.ssafy.bango.global.exception.CustomException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

    public static final DateTimeFormatter YYYYMMDD_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static LocalDate parseYyyyMMdd(String dateStr) {
        try {
            return LocalDate.parse(dateStr, YYYYMMDD_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다: " + dateStr, e);
        }

    }

//    public static String formatYyyyMMdd(LocalDate date) {
//        return date.format(YYYYMMDD_FORMATTER);
//    }
}