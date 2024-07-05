package com.jsj.backend.vworld.wmts.dto;

import lombok.*;

/**
 * VWorld WMTS API 요청을 위한 DTO 클래스.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class VWorldWMTSApiRequest {

    /**
     * 발급받은 API 키
     */
    private String key;

    /**
     * 요청 레이어 종류
     */
    private String layer;

    /**
     * 타일 매트릭스 (지도 레벨)
     */
    private Integer tileMatrix;

    /**
     * 타일 행 (Y 좌표)
     */
    private Integer tileRow;

    /**
     * 타일 열 (X 좌표)
     */
    private Integer tileCol;

    /**
     * 타일 확장자 (기본값: .png)
     */
    @Builder.Default
    private String tileType = ".png";
}
