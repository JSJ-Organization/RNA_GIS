package com.jsj.backend.vworld.wmts.dto;

import org.springframework.stereotype.Service;

/**
 * VWorld WMTS 요청 매퍼 클래스.
 * 이 클래스는 경로 변수를 VWorldWMTSApiRequest 객체로 매핑하는 기능을 제공합니다.
 */
@Service
public class VWorldWMTSMapper {

    /**
     * 경로 변수를 VWorldWMTSApiRequest 객체로 매핑합니다.
     *
     * @param apiKeyVworld VWorld API 키
     * @param layer 요청 레이어 종류
     * @param z 지도 레벨
     * @param y 타일 행 (Y 좌표)
     * @param x 타일 열 (X 좌표)
     * @param tileType 타일 확장자
     * @return VWorldWMTSApiRequest 객체
     */
    public VWorldWMTSApiRequest pathVariableToRequest(
            String apiKeyVworld,
            String layer,
            int z,
            int y,
            int x,
            String tileType
    ) {
        return VWorldWMTSApiRequest.builder()
                .key(apiKeyVworld)
                .layer(layer)
                .tileMatrix(z)
                .tileRow(y)
                .tileCol(x)
                .tileType(tileType)
                .build();
    }
}
