package com.jsj.backend.frcnRentInfo;

import lombok.*;

import java.util.List;

/**
 * 농기계 임대 정보 API 응답을 나타내는 클래스.
 * 이 클래스는 외부 API로부터 받은 응답 데이터를 포함합니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FrcnRentInfoApiResponse {

    /** 응답 정보를 담고 있는 루트 객체 */
    private Response response;

    /**
     * 응답의 상세 정보를 담고 있는 내부 클래스.
     */
    @Data
    @NoArgsConstructor
    public static class Response {

        /** 응답 헤더 정보 */
        private Header header;

        /** 응답 바디 정보 */
        private Body body;
        /**
         * 응답 헤더 정보를 담고 있는 내부 클래스.
         */
        @Data
        @NoArgsConstructor
        public static class Header {

            /** 결과 코드 */
            private String resultCode;

            /** 결과 메시지 */
            private String resultMsg;

            /** 응답 형식 */
            private String type;
        }

        /**
         * 응답 바디 정보를 담고 있는 내부 클래스.
         */
        @Data
        @NoArgsConstructor
        public static class Body {

            /** 응답 결과 항목 목록 */
            private List<Item> items;

            /**
             * 응답 결과 항목을 담고 있는 내부 클래스.
             */
            @Data
            @NoArgsConstructor
            public static class Item {
                /** 사업소명 */
                private String officeNm;
                /** 사업소 전화번호 */
                private String officePhoneNumber;
                /** 소재지 도로명 주소 */
                private String rdnmadr;
                /** 소재지 지번 주소 */
                private String lnmadr;
                /** 위도 y */
                private double latitude;
                /** 경도 x */
                private double longitude;
                /** 트랙터 및 작업기 보유 대수 */
                private String trctorHoldCo;
                /** 경운기 및 작업기 보유 대수 */
                private String cultvtHoldCo;
                /** 관리기 및 작업기 보유 대수 */
                private String manageHoldCo;
                /** 땅속 작물 수확기 보유 대수 */
                private String harvestHoldCo;
                /** 탈곡기 및 정선 작업기 보유 대수 */
                private String thresherHoldCo;
                /** 자주형 파종기 보유 대수 */
                private String planterHoldCo;
                /** 이앙 작업기 보유 대수 */
                private String transplantHoldCo;
                /** 벼 수확 및 운반 작업기 보유 대수 */
                private String rcepntHoldCo;
                /** 기타 임대 농기계 보유 정보 */
                private String etcRentHoldCo;
                /** 관리 기관 전화번호 */
                private String phoneNumber;
                /** 관리 기관명 */
                private String institutionNm;
                /** 데이터 기준 일자 */
                private String referenceDate;
                /** 제공 기관 코드 */
                private String instt_code;
            }
        }
    }
}
