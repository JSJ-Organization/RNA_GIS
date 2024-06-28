package com.jsj.backend.search.juso;

import lombok.*;

import java.util.List;

/**
 * 검색 결과를 담는 DTO 클래스.
 * 검색된 주소 목록과 페이지 정보를 포함합니다.
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JusoApiResponse {

    /**
     * 총 검색 데이터 수 (필수).
     */
    private String totalCount;

    /**
     * 현재 페이지 번호 (필수).
     */
    private Integer currentPage;

    /**
     * 페이지당 출력할 결과 Row 수 (필수).
     */
    private Integer countPerPage;

    /**
     * 에러 코드 (필수).
     */
    private String errorCode;

    /**
     * 에러 메시지 (필수).
     */
    private String errorMessage;

    /**
     * 주소 목록.
     */
    private List<Juso> juso;

    /**
     * 주소 정보를 담는 내부 클래스.
     */
    @Data
    @NoArgsConstructor
    public static class Juso {
        /**
         * 전체 도로명주소 (필수).
         */
        private String roadAddr;

        /**
         * 도로명주소(참고항목 제외) (필수).
         */
        private String roadAddrPart1;

        /**
         * 도로명주소 참고항목 (선택).
         */
        private String roadAddrPart2;

        /**
         * 지번 정보 (필수).
         */
        private String jibunAddr;

        /**
         * 도로명주소(영문) (필수).
         */
        private String engAddr;

        /**
         * 우편번호 (필수).
         */
        private String zipNo;

        /**
         * 행정구역코드 (필수).
         */
        private String admCd;

        /**
         * 도로명코드 (필수).
         */
        private String rnMgtSn;

        /**
         * 건물관리번호 (필수).
         */
        private String bdMgtSn;

        /**
         * 상세건물명 (선택).
         */
        private String detBdNmList;

        /**
         * 건물명 (선택).
         */
        private String bdNm;

        /**
         * 공동주택여부 (1:공동주택, 0: 비공동주택) (필수).
         */
        private String bdKdcd;

        /**
         * 시도명 (필수).
         */
        private String siNm;

        /**
         * 시군구명 (선택).
         */
        private String sggNm;

        /**
         * 읍면동명 (필수).
         */
        private String emdNm;

        /**
         * 법정리명 (선택).
         */
        private String liNm;

        /**
         * 도로명 (필수).
         */
        private String rn;

        /**
         * 지하여부 (0:지상, 1:지하) (필수).
         */
        private String udrtYn;

        /**
         * 건물본번 (필수).
         */
        private Integer buldMnnm;

        /**
         * 건물부번 (부번이 없는 경우 0) (필수).
         */
        private Integer buldSlno;

        /**
         * 산여부 (0:대지, 1:산) (필수).
         */
        private String mtYn;

        /**
         * 지번본번(번지) (필수).
         */
        private Integer lnbrMnnm;

        /**
         * 지번부번(호) (부번이 없는 경우 0) (필수).
         */
        private Integer lnbrSlno;

        /**
         * 읍면동일련번호 (필수).
         */
        private String emdNo;

        /**
         * 변동이력여부 (0: 현행 주소정보, 1: 변동된 주소정보) (필수).
         * 2020년 12월 8일 추가된 항목.
         * 요청 변수의 keyword(검색어)가 변동된 주소정보에서 검색된 정보.
         */
        private String hstryYn;

        /**
         * 관련지번 (필수).
         * 2020년 12월 8일 추가된 항목.
         */
        private String relJibun;

        /**
         * 관할주민센터 (필수).
         * 2020년 12월 8일 추가된 항목.
         */
        private String hemdNm;
    }
}