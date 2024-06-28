package com.jsj.backend.exception;

/**
 * JSON 처리 중 발생하는 예외 클래스.
 * 이 클래스는 JSON 처리 도중 발생하는 예외를 나타냅니다.
 */
public class JsonProcessingCustomException extends RuntimeException {

    /**
     * 주어진 메시지로 새로운 JsonProcessingCustomException 인스턴스를 생성합니다.
     *
     * @param message 예외 메시지
     */
    public JsonProcessingCustomException(String message) {
        super(message);
    }

    /**
     * 주어진 메시지와 원인으로 새로운 JsonProcessingCustomException 인스턴스를 생성합니다.
     *
     * @param message 예외 메시지
     * @param cause 예외의 원인
     */
    public JsonProcessingCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
