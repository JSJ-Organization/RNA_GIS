package com.jsj.backend.frcnRentInfo.dto;

import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfo;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * 농기계 임대 정보 매퍼 클래스.
 */
@Service
public class FrcnRentInfoMapper {

    /**
     * EPSG:4326 좌표계 SRID.
     */
    private static final int SRID = 4326;
    private final GeometryFactory geometryFactory;

    /**
     * FrcnRentInfoMapper 생성자.
     * PrecisionModel과 SRID를 사용하여 GeometryFactory를 초기화합니다.
     */
    public FrcnRentInfoMapper() {
        this.geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);
    }

    /**
     * 주어진 x, y 좌표를 Point 객체로 변환합니다.
     *
     * @param x 경도
     * @param y 위도
     * @return Point 객체
     */
    public Point XYtoPoint(double x, double y) {
        Coordinate coordinate = new Coordinate(x, y);
        return Optional.ofNullable(coordinate)
                .map(geometryFactory::createPoint)
                .orElse(null);
    }

    /**
     * API 응답 데이터를 엔티티 리스트로 변환합니다.
     *
     * @param apiResponse API 응답 객체
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> apiToEntity(FrcnRentInfoApiResponse apiResponse) {
        List<FrcnRentInfoApiResponse.Response.Body.Item> items = apiResponse.getResponse().getBody().getItems();
        if (items == null || items.isEmpty()) {
            return emptyList();
        }
        return items.stream().map(item -> FrcnRentInfo.builder()
                        .officeNm(item.getOfficeNm())
                        .officePhoneNumber(item.getOfficePhoneNumber())
                        .rdnmadr(item.getRdnmadr())
                        .lnmadr(item.getLnmadr())
                        .geometry(XYtoPoint(item.getLongitude(), item.getLatitude()))
                        .trctorHoldCo(item.getTrctorHoldCo())
                        .cultvtHoldCo(item.getCultvtHoldCo())
                        .manageHoldCo(item.getManageHoldCo())
                        .harvestHoldCo(item.getHarvestHoldCo())
                        .thresherHoldCo(item.getThresherHoldCo())
                        .planterHoldCo(item.getPlanterHoldCo())
                        .transplantHoldCo(item.getTransplantHoldCo())
                        .rcepntHoldCo(item.getRcepntHoldCo())
                        .etcRentHoldCo(item.getEtcRentHoldCo())
                        .phoneNumber(item.getPhoneNumber())
                        .institutionNm(item.getInstitutionNm())
                        .referenceDate(item.getReferenceDate())
                        .instt_code(item.getInstt_code())
                        .build())
                .collect(toList());
    }

    /**
     * 엔티티 리스트를 응답 객체 리스트로 변환합니다.
     *
     * @param entities FrcnRentInfo 엔티티 리스트
     * @return FrcnRentInfoResponse 응답 객체 리스트
     */
    public List<FrcnRentInfoResponse> entityToResponse(List<FrcnRentInfo> entities) {
        if (entities == null || entities.isEmpty()) {
            return emptyList();
        }
        return entities.stream()
                .map(entity -> FrcnRentInfoResponse.builder()
                        .id(entity.getId())
                        .x(entity.getGeometry().getCoordinate().getX())
                        .y(entity.getGeometry().getCoordinate().getY())
                        .roadNameAddress(entity.getRdnmadr())
                        .parcelAddress(entity.getLnmadr())
                        .officeNm(entity.getOfficeNm())
                        .officePhoneNumber(entity.getOfficePhoneNumber())
                        .trctorHoldCo(entity.getTrctorHoldCo())
                        .cultvtHoldCo(entity.getCultvtHoldCo())
                        .manageHoldCo(entity.getManageHoldCo())
                        .harvestHoldCo(entity.getHarvestHoldCo())
                        .thresherHoldCo(entity.getThresherHoldCo())
                        .planterHoldCo(entity.getPlanterHoldCo())
                        .transplantHoldCo(entity.getTransplantHoldCo())
                        .rcepntHoldCo(entity.getRcepntHoldCo())
                        .etcRentHoldCo(entity.getEtcRentHoldCo())
                        .phoneNumber(entity.getPhoneNumber())
                        .institutionNm(entity.getInstitutionNm())
                        .referenceDate(entity.getReferenceDate())
                        .instt_code(entity.getInstt_code())
                        .build())
                .collect(toList());
    }
}
