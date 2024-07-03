package com.jsj.backend.vworld.wmts;

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
public class VWorldWMTSApiRequest {

    /**
     * 발급받은 API 키
     */
    private String key;
    private String layer;
    private Integer tileMatrix;
    private Integer tileRow;
    private Integer tileCol;
    @Builder.Default
    private String tileType = ".png";

}