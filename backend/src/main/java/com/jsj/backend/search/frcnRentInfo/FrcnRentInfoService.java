package com.jsj.backend.search.frcnRentInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FrcnRentInfoService {
    private final FrcnRentInfoRepository frcnRentInfoRepository;
    private final FrcnRentInfoMapper frcnRentInfoMapper;

    public void saveAll(FrcnRentInfoApiResponse apiResponse) {
        List<FrcnRentInfo> frcnRentInfos = frcnRentInfoMapper.apiToEntity(apiResponse);
        frcnRentInfoRepository.saveAll(frcnRentInfos);
    }
}
