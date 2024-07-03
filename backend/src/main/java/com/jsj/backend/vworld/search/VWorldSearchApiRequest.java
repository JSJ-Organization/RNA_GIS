package com.jsj.backend.vworld.search;

import lombok.*;

import static com.jsj.backend.vworld.SpatialReferenceSystem.WGS84;

/**
 * VWorld Search API 요청을 위한 DTO 클래스.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class VWorldSearchApiRequest {

    /**
     * 요청 서비스명 (기본값: "search")
     * 필수 여부: O (선택)
     */
    @Builder.Default
    private String service = "search";

    /**
     * 요청 서비스 버전 (기본값: "2.0")
     * 필수 여부: O (선택)
     */
    @Builder.Default
    private String version = "2.0";

    /**
     * 요청 서비스 오퍼레이션 (기본값: "search")
     * 필수 여부: M (필수)
     */
    @Builder.Default
    private String request = "search";

    /**
     * 발급받은 API 키
     * 필수 여부: M (필수)
     */
    private String key;

    /**
     * 응답 결과 포맷 (기본값: "json")
     * 필수 여부: O (선택)
     * 유효값: "json", "xml"
     */
    @Builder.Default
    private String format = "json";

    /**
     * 에러 응답 결과 포맷, 생략 시 format 파라미터에 지정된 포맷으로 설정 (기본값: "json")
     * 필수 여부: O (선택)
     * 유효값: "json", "xml"
     */
    @Builder.Default
    private String errorFormat = "json";

    /**
     * 한 페이지에 출력될 응답 결과 건수 (기본값: 10, 최소값: 1, 최대값: 1000)
     * 필수 여부: O (선택)
     */
    @Builder.Default
    private Integer size = 5;

    /**
     * 응답 결과 페이지 번호 (기본값: 1)
     * 필수 여부: O (선택)
     */
    @Builder.Default
    private Integer page = 1;

    /**
     * 검색 키워드 (예: 장소(건물명, 시설명, 기관/상호명 등), 주소, 행정구역, 도로명)
     * 필수 여부: M (필수)
     */
    private String query;

    /**
     * 검색 대상 (PLACE: 장소, ADDRESS: 주소, DISTRICT: 행정구역, ROAD: 도로명)
     * 필수 여부: M (필수)
     */
    @Builder.Default
    private String type = "address";

    /**
     * 검색 대상에 따른 하위 유형
     * - 장소: O (선택)
     * - 주소: M (필수) road: 도로명, parcel: 지번
     * - 행정구역: M (필수)
     */
    @Builder.Default
    private String category = "road";

    /**
     * 검색 영역 내의 대상만 검색 (포맷: minx,miny,maxx,maxy)
     * 필수 여부: O (선택)
     */
    private String bbox;

    /**
     * 응답 결과 좌표계 (기본값: "EPSG:4326")
     * 필수 여부: O (선택)
     */
    @Builder.Default
    private String crs = WGS84.getCode();

    /**
     * format 값이 "json"일 경우 callback 함수를 지원합니다.
     * 필수 여부: O (선택)
     */
    private String callback;
}