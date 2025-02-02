package com.jsj.backend.frcnRentInfo.log;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * VWorld API 요청 및 응답 로그를 나타내는 클래스.
 * 이 클래스는 VWorld API에 대한 요청과 그 응답을 기록합니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FrcnRentInfoLog {
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 사용자 입력값
     */
    private String searchQuery;
    /**
     * 요청 상태,
     * 예: OK, NOT_FOUND, ERROR)
     */
    private String status;
    /**
     * 전체 결과 건수
     */
    @Builder.Default
    private Integer total = 0;
    /**
     * 오류 코드 (있을 경우)
     */
    private String errorCode;
    /**
     * 오류 메시지 (있을 경우)
     */
    private String errorText;
    /**
     * 로그 생성 시간
     */
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
