package com.jsj.backend.vworld.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * VWorld 검색 응답 DTO 클래스.
 * 이 클래스는 VWorld 검색 API 응답 데이터를 담고 있습니다.
 */
@Schema(description = "주소 정보 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class VWorldSearchResponse {
    /**
     * x 좌표
     */
    @Schema(description = "X 좌표")
    private double x;
    /**
     * y 좌표
     */
    @Schema(description = "Y 좌표")
    private double y;
    /**
     * 도로명 주소
     */
    @Schema(description = "도로명 주소")
    private String roadNameAddress;
    /**
     * 지번 주소
     */
    @Schema(description = "지번 주소")
    private String parcelAddress;
    /**
     * 우편번호
     */
    @Schema(description = "우편번호")
    private Integer zipcode;
    /**
     * 건물명
     */
    @Schema(description = "건물명")
    private String bldnm;
    /**
     * 건물 상세명
     */
    @Schema(description = "건물 상세명")
    private String bldnmdc;
}
