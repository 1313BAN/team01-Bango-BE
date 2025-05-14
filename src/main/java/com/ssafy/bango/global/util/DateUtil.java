package com.ssafy.bango.global.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final DateTimeFormatter YYYYMMDD_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static LocalDate parseYyyyMMdd(String dateStr) {
        return LocalDate.parse(dateStr, YYYYMMDD_FORMATTER);
    }

//    public static String formatYyyyMMdd(LocalDate date) {
//        return date.format(YYYYMMDD_FORMATTER);
//    }
}