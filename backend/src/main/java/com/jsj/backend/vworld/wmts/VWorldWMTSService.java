package com.jsj.backend.vworld.wmts;

import com.jsj.backend.vworld.wmts.dto.VWorldWMTSApiRequest;
import com.jsj.backend.vworld.wmts.dto.VWorldWMTSMapper;
import com.jsj.backend.vworld.wmts.httpClient.VWorldWMTSApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VWorldWMTSService {
    private final VWorldWMTSApiClient vWorldWMTSApiClient;
    private final VWorldWMTSMapper vWorldWMTSMapper;
    @Value("${api.vworld.key}")
    private String API_KEY_VWORLD;

    public byte[] getTile(String layer, int z, int y, int x, String tileType) {

        VWorldWMTSApiRequest request = vWorldWMTSMapper.pathVariableToRequest(
                API_KEY_VWORLD,
                layer,
                z,
                y,
                x,
                tileType
        );

        return vWorldWMTSApiClient.getTile(request);
    }
}
