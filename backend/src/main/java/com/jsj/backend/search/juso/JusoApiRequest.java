package com.jsj.backend.search.juso;

import lombok.*;

/**
 * 도로명 주소 검색 API 요청을 위한 DTO 클래스.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JusoApiRequest {

    /**
     * 신청 시 발급받은 승인키 (필수).
     */
    private String confmKey;

    /**
     * 현재 페이지 번호 ( n>0 ) (필수, 기본값: 1).
     */
    private Integer currentPage;

    /**
     * 페이지당 출력할 결과 Row 수 ( 0<n<=100 ) (필수, 기본값: 10).
     */
    private Integer countPerPage;

    /**
     * 주소 검색어 (필수).
     */
    private String keyword;

    /**
     * 검색 결과 형식 설정 (선택, 기본값: XML).
     * 'json' 입력 시 JSON 형식의 결과 제공.
     */
    private String resultType;

    /**
     * 변동된 주소정보 포함 여부 (선택, 기본값: N).
     * 2020년 12월 8일 추가된 항목.
     */
    private String hstryYn;

    /**
     * 정확도 정렬 (선택, 기본값: none).
     * 2020년 12월 8일 추가된 항목.
     * 정렬 옵션: 'road' (도로명 포함), 'location' (지번 포함).
     */
    private String firstSort;

    /**
     * 출력 결과에 추가된 항목 제공 여부 (선택, 기본값: N).
     * 2020년 12월 8일 추가된 항목.
     * 추가 제공 항목: hstryYn, relJibun, hemdNm.
     */
    private String addInfoYn;
}
