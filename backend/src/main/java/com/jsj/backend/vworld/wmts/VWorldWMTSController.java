package com.jsj.backend.vworld.wmts;

import com.jsj.backend.search.dto.SearchResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * VWorld WMTS 타일 요청을 처리하는 REST 컨트롤러 클래스.
 * 이 클래스는 클라이언트로부터의 타일 요청을 받아서 처리합니다.
 */
@RestController
@RequiredArgsConstructor
@Schema(description = "VWorld WMTS 타일 요청을 처리하는 REST 컨트롤러 클래스")
@ApiResponse(content = @Content(schema = @Schema(implementation = SearchResponse.class)))
public class VWorldWMTSController {

    private final VWorldWMTSService vWorldWMTSService; // VWorld WMTS 서비스 객체

    /**
     * 주어진 레이어와 좌표로 타일 이미지를 가져옵니다.
     *
     * @param layer 요청 레이어 종류 (Base, white, midnight, Hybrid, Satellite)
     * @param z tileMatrix 지도 레벨 (Base : 6~19, white : 6~18, midnight : 6~18, Hybrid : 6~19, Satellite : 6~19)
     * @param y tileRow WGS 84 Google Index Y 좌표값 (위도 (y 좌표): 0 <= 2^z − 1)
     * @param x tileCol WGS 84 Google Index X 좌표값 (경도 (x 좌표): 0 <= 2^z − 1)
     * @param tileType 타일 확장자 (Base : png, white : png, midnight : png, Hybrid : png, Satellite : jpeg)
     * @return 타일 이미지 데이터를 담고 있는 ResponseEntity 객체
     */
    @GetMapping("/api/v1/search/wmts/{layer}/{z}/{y}/{x}.{tileType}")
    public ResponseEntity<?> getBytesTile(
            @Parameter(description = "layer 요청 레이어 종류 (Base, white, midnight, Hybrid, Satellite)", example = "Base") @PathVariable(name = "layer") String layer,
            @Parameter(description = "z tileMatrix 지도레벨 (Base : 6~19, white : 6~18, midnight : 6~18, Hybrid : 6~19, Satellite : 6~19)", example = "11") @PathVariable(name = "z") int z,
            @Parameter(description = "y tileRow WGS 84 Google Index Y좌표값 (위도 (y 좌표): 0 <= 2^z − 1)", example = "793") @PathVariable(name = "y") int y,
            @Parameter(description = "x tileCol WGS 84 Google Index X좌표값 (경도 (x 좌표): 0 <= 2^z − 1)", example = "1746") @PathVariable(name = "x") int x,
            @Parameter(description = "tileType Tile 확장자 (Base : png, white : png, midnight : png, Hybrid : png, Satellite : jpeg)", example = "11") @PathVariable(name = "tileType") String tileType
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/"+tileType);
        byte[] tile = vWorldWMTSService.getTile(layer, z, y, x, tileType);
        return ResponseEntity.ok().headers(headers).body(tile);
    }
}
