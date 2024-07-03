package com.jsj.backend.frcnRentInfo;

import lombok.*;

/**
 * 농기계 임대 정보 API 요청을 나타내는 클래스.
 * 이 클래스는 외부 API에 대한 요청 파라미터를 포함합니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FrcnRentInfoApiRequest {
    /** API 키 */
    private String serviceKey;
    /** 페이지 번호 */
    @Builder.Default
    private Integer pageNo = 1;
    /** 한 페이지가 담을, 최대 데이터 */
    @Builder.Default
    private Integer numOfRows = 1000;
    /** 응답 타입(json, xml) */
    @Builder.Default
    private String type = "json";

}
