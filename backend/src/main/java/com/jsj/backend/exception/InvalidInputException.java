package com.jsj.backend.exception;

/**
 * 입력 값이 유효하지 않을 때 발생하는 예외 클래스.
 * 이 클래스는 입력 값이 유효하지 않을 경우 발생하는 예외를 나타냅니다.
 */
public class InvalidInputException extends RuntimeException {

    /**
     * 주어진 메시지로 새로운 InvalidInputException 인스턴스를 생성합니다.
     *
     * @param message 예외 메시지
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
