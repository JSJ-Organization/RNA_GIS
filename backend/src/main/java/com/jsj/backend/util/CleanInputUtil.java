package com.jsj.backend.util;

/**
 * 입력 문자열을 정화하는 유틸리티 클래스.
 * 이 클래스는 입력 문자열에서 한글, 영어, 숫자만 허용하고 SQL 예약어와 특수문자를 제거하는 메서드를 제공합니다.
 */
public class CleanInputUtil {

    // SQL 예약어와 특수문자 패턴 정의
    private static final String[] KEYWORDS = {
            "or ",
            "select ",
            "insert ",
            "delete ",
            "update ",
            "create ",
            "drop ",
            "exec ",
            "union ",
            "fetch ",
            "declare ",
            "truncate "
    };

    private static final String[] PATTERNS = {
            "'\\+or\\+'1'='1",
            "'\\ and\\ 'f'='f",
            "%27\\+and\\+",
            "UNION\\+SELECT",
            "%27\\+and\\+%27x%27%3D"
    };

    /**
     * 입력 문자열에서 한글, 영어, 숫자만 허용하고 나머지는 제거합니다.
     *
     * @param input 처리할 입력 문자열
     * @return 한글, 영어, 숫자만 남은 문자열
     */
    private static String sanitizeInput(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.replaceAll("[^a-zA-Z0-9가-힣]", "");
    }

    /**
     * 입력 문자열에서 SQL 예약어와 특수문자를 제거합니다.
     *
     * @param input 처리할 입력 문자열
     * @return SQL 예약어와 특수문자가 제거된 문자열
     */
    private static String removeSQLKeywordsAndPatterns(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        for (String keyword : KEYWORDS) {
            input = input.replaceAll("(?i)\\b" + keyword + "\\b", "");
        }
        for (String pattern : PATTERNS) {
            input = input.replaceAll("(?i)" + pattern, "");
        }
        return input;
    }

    /**
     * 입력 문자열을 정화하고 SQL 예약어와 패턴을 제거합니다.
     *
     * @param input 처리할 입력 문자열
     * @return 정화된 문자열
     */
    public static String cleanInput(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String sanitizedInput = sanitizeInput(input);
        return removeSQLKeywordsAndPatterns(sanitizedInput);
    }
}
