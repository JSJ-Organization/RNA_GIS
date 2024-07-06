package com.jsj.backend.vworld.wmts;

import com.jsj.backend.vworld.log.VWorldLog;
import com.jsj.backend.vworld.log.VWorldLogRepository;
import com.jsj.backend.vworld.wmts.dto.VWorldWMTSApiRequest;
import com.jsj.backend.vworld.wmts.dto.VWorldWMTSMapper;
import com.jsj.backend.vworld.wmts.httpClient.VWorldWMTSApiClient;
import com.sun.jdi.InvalidTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.jsj.backend.util.ValidationUtil.validateQuery;

/**
 * VWorld WMTS 타일 서비스를 제공하는 클래스.
 * 이 클래스는 VWorld WMTS API를 통해 타일 이미지를 가져오는 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VWorldWMTSService {

    private final VWorldLogRepository vWorldLogRepository; // VWorld 로그 저장소
    private final VWorldWMTSApiClient vWorldWMTSApiClient; // VWorld WMTS API 클라이언트
    private final VWorldWMTSMapper vWorldWMTSMapper; // VWorld WMTS 요청 매퍼

    @Value("${api.vworld.key}")
    private String API_KEY_VWORLD; // 외부 설정 파일에서 주입받는 VWorld API 키

    private void logVWorldWMTApiResponse(VWorldWMTSApiRequest request, Map<String, String> error, byte[] response) {
        String errorCode = null;
        String errorText = null;
        String status = null;
        Integer total;
        if (response == null) {
            status = "NOT_FOUND";
            total = 0;
            errorText = "데이터가 존재하지 않습니다";
        } else {
            status = "OK";
            total = 1;
        }

        vWorldLogRepository.save(VWorldLog.builder()
                .searchQuery(String.format(
                        "layer: %s, z: %d, y: %d, x: %d, type: %s",
                        request.getLayer(),
                        request.getTileMatrix(),
                        request.getTileRow(),
                        request.getTileCol(),
                        request.getTileType()))
                .status(status)
                .total(total)
                .errorCode(errorCode)
                .errorText(errorText)
                .build());
    }

    /**
     * 주어진 레이어와 좌표로 타일 이미지를 가져옵니다.
     *
     * @param layer    타일 레이어
     * @param z        줌 레벨
     * @param y        Y 좌표
     * @param x        X 좌표
     * @param tileType 타일 유형
     * @return 타일 이미지 데이터를 담고 있는 byte 배열
     */
    public byte[] getTile(String layer, int z, int y, int x, String tileType) {
        Map<String, String> errorMap = new HashMap<>();
        try {
            errorMap = validateQuery(layer, z, y, x, tileType);
        } catch (InvalidTypeException e) {
            errorMap.put("errorCode", "INVALID_TYPE");
            errorMap.put("errorText", e.getMessage());
        }
        // 요청 객체 생성
        VWorldWMTSApiRequest request = vWorldWMTSMapper.pathVariableToRequest(
                API_KEY_VWORLD,
                layer,
                z,
                y,
                x,
                tileType
        );

        // 타일 이미지 데이터 반환
        byte[] response = vWorldWMTSApiClient.getTile(request);
        logVWorldWMTApiResponse(request, errorMap, response);
        return response;
    }
}
