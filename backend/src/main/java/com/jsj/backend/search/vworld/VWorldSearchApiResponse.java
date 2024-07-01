package com.jsj.backend.search.vworld;

import lombok.*;

import java.util.List;

/**
 * VWorld 검색 API의 응답을 나타내는 클래스.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class VWorldSearchApiResponse {

    /**
     * 응답 정보를 담고 있는 루트 객체
     */
    private Response response;

    /**
     * 응답의 상세 정보를 담고 있는 내부 클래스.
     */
    @Data
    @NoArgsConstructor
    public static class Response {
        /**
         * 요청 서비스 정보
         */
        private Service service;

        /**
         * 처리 결과의 상태 표시 (유효값: OK, NOT_FOUND, ERROR)
         */
        private String status;

        /**
         * 응답결과 건수 정보
         */
        private Record record;

        /**
         * 응답결과 페이지 정보
         */
        private Page page;

        /**
         * 응답결과 루트
         */
        private Result result;

        /**
         * 에러 정보.
         */
        private Error error;

        /**
         * 서비스 정보를 담고 있는 내부 클래스.
         */
        @Data
        @NoArgsConstructor
        public static class Service {
            /**
             * 요청 서비스명
             */
            private String name;

            /**
             * 요청 서비스 버전
             */
            private String version;

            /**
             * 요청 서비스 오퍼레이션 이름
             */
            private String operation;

            /**
             * 응답결과 생성 시간
             */
            private String time;
        }

        /**
         * 레코드 정보를 담고 있는 내부 클래스.
         */
        @Data
        @NoArgsConstructor
        public static class Record {
            /**
             * 전체 결과 건수
             */
            private Integer total;

            /**
             * 현재 반환된 결과 건수
             */
            private Integer current;
        }

        /**
         * 페이지 정보를 담고 있는 내부 클래스.
         */
        @Data
        @NoArgsConstructor
        public static class Page {
            /**
             * 전체 페이지 수
             */
            private Integer total;

            /**
             * 현재 페이지 번호
             */
            private Integer current;

            /**
             * 페이지 당 반환되는 결과 건수
             */
            private Integer size;
        }

        /**
         * 결과 정보를 담고 있는 내부 클래스.
         */
        @Data
        @NoArgsConstructor
        public static class Result {
            /**
             * 응답결과 좌표계
             */
            private String crs;

            /**
             * 요청 검색 대상
             */
            private String type;

            /**
             * 응답결과 목록
             */
            private List<Item> items;

            /**
             * 응답결과 상세정보, 여러 건일 경우 반복
             */
            @Data
            @NoArgsConstructor
            public static class Item {
                /**
                 * 주소의 ID (PNU 지번 코드)
                 */
                private String id;

                /**
                 * 주소 정보
                 */
                private Address address;

                /**
                 * 주소 좌표
                 */
                private Point point;

                /**
                 * 주소 정보를 담고 있는 내부 클래스.
                 */
                @Data
                @NoArgsConstructor
                public static class Address {
                    /**
                     * 우편번호
                     */
                    private Integer zipcode;

                    /**
                     * 요청한 주소의 유형
                     */
                    private String category;

                    /**
                     * 도로 주소
                     */
                    private String road;

                    /**
                     * 지번 주소
                     */
                    private String parcel;

                    /**
                     * 건물명
                     */
                    private String bldnm;

                    /**
                     * 건물 상세명
                     */
                    private String bldnmdc;
                }

                /**
                 * 좌표 정보를 담고 있는 내부 클래스.
                 */
                @Data
                @NoArgsConstructor
                public static class Point {
                    /**
                     * x 좌표
                     */
                    private String x;

                    /**
                     * y 좌표
                     */
                    private String y;
                }
            }
        }

        /**
         * 에러 정보를 담고 있는 내부 클래스.
         */
        @Data
        @NoArgsConstructor
        public static class Error {
            /**
             * 에러 레벨
             */
            private int level;
            /**
             * 에러 코드
             */
            private String code;
            /**
             * 에러 메시지
             */
            private String text;
        }
    }
}
