package com.jsj.backend.vworld.wmts.dto;

import com.jsj.backend.vworld.wmts.dto.VWorldWMTSApiRequest;
import org.springframework.stereotype.Service;

@Service
public class VWorldWMTSMapper {
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
