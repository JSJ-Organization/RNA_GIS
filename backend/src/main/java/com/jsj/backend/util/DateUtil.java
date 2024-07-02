package com.jsj.backend.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 날짜와 관련된 유틸리티 메서드를 제공하는 클래스.
 */
public class DateUtil {

    /** 날짜 형식을 정의하는 상수 (yyyyMMdd) */
    private static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * 주어진 날짜 문자열을 yyyy-MM-dd 형식으로 변환합니다.
     *
     * @param dateString 변환할 날짜 문자열 (yyyyMMdd 형식)
     * @return 변환된 날짜 문자열 (yyyy-MM-dd 형식)
     */
    public static String formatDateString(String dateString) {
        DateTimeFormatter originalFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
        DateTimeFormatter targetFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, originalFormat);
        return date.format(targetFormat);
    }

    /**
     * 오늘 날짜를 yyyyMMdd 형식의 문자열로 반환합니다.
     *
     * @return 오늘 날짜를 나타내는 문자열 (yyyyMMdd 형식)
     */
    public static String getTodayDateString() {
        // 현재 날짜를 구함
        LocalDate today = LocalDate.now();

        // 날짜를 지정된 형식의 문자열로 변환
        return today.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
