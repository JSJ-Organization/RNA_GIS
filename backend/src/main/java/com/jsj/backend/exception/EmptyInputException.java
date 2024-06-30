package com.jsj.backend.exception;

/**
 * 입력이 비어 있을 때 발생하는 예외 클래스.
 * 이 클래스는 입력 값이 비어 있을 경우 발생하는 예외를 나타냅니다.
 */
public class EmptyInputException extends RuntimeException {

    /**
     * 주어진 메시지로 새로운 EmptyInputException 인스턴스를 생성합니다.
     *
     * @param message 예외 메시지
     */
    public EmptyInputException(String message) {
        super(message);
    }
}
