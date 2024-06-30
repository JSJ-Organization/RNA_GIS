package com.jsj.backend.handler;

import com.jsj.backend.exception.EmptyInputException;
import com.jsj.backend.exception.InvalidInputException;
import com.jsj.backend.exception.JsonProcessingCustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 전역 예외 처리기 클래스.
 * 이 클래스는 애플리케이션 전반에서 발생하는 예외를 처리합니다.
 *
 * @RestControllerAdvice 애노테이션을 사용하여 스프링이 이 클래스를 전역 예외 처리기로 인식합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * EmptyInputException 예외를 처리합니다.
     *
     * @param exception 처리할 EmptyInputException 예외
     * @return 예외 메시지를 포함한 BAD_REQUEST 상태의 ResponseEntity 객체
     */
    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<String> handle(EmptyInputException exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(exception.getMessage());
    }

    /**
     * InvalidInputException 예외를 처리합니다.
     *
     * @param exception 처리할 InvalidInputException 예외
     * @return 예외 메시지를 포함한 BAD_REQUEST 상태의 ResponseEntity 객체
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(exception.getMessage());
    }

    /**
     * JsonProcessingCustomException 예외를 처리합니다.
     *
     * @param exception 처리할 JsonProcessingCustomException 예외
     * @return 예외 메시지를 포함한 INTERNAL_SERVER_ERROR 상태의 ResponseEntity 객체
     */
    @ExceptionHandler(JsonProcessingCustomException.class)
    public ResponseEntity<String> handle(JsonProcessingCustomException exception) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
