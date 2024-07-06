package com.jsj.backend.util;

import com.sun.jdi.InvalidTypeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtil {

    public static void validateQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            String errorMessage = "검색이 불가능합니다:: 키워드가 빈 값입니다.";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void validateQuery(double x, double y, Integer distance) throws InvalidTypeException {
        if (distance == null) {
            String errorMessage = "distance 가 빈 값입니다:: distance: null";
            log.error(errorMessage);
            throw new InvalidTypeException(errorMessage);
        } else if (distance <= 0) {
            String errorMessage = String.format("distance 가 0보다 큰 값이어야 합니다:: distance: %d", distance);
            log.error(errorMessage);
            throw new InvalidTypeException(errorMessage);
        } else if (Double.isNaN(x) || Double.isNaN(y)) {
            String errorMessage = String.format("x, y 타입이 잘못되었습니다:: x: %f , y: %f", x, y);
            log.error(errorMessage);
            throw new InvalidTypeException(errorMessage);
        }
    }
}
