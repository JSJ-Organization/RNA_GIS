package com.jsj.backend.search.frcnRentInfo;

import jakarta.persistence.Column;
import lombok.*;
import org.locationtech.jts.geom.Geometry;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FrcnRentInfoResponse {
    /** 사업소명 */
    private String officeNm;
    /** 사업소 전화번호 */
    private String officePhoneNumber;
    /** 소재지 도로명 주소 */
    private String rdnmadr;
    /** 소재지 지번 주소 */
    private String lnmadr;
    /** 위도, 경도를 포함한 좌표 객체 EPSG:4326*/
    @Column(columnDefinition = "Geometry")
    private Geometry geometry;
    /** 트랙터 및 작업기 보유 대수 */
    private String trctorHoldCo;
    /** 경운기 및 작업기 보유 대수 */
    private String cultvtHoldCo;
    /** 관리기 및 작업기 보유 대수 */
    private String manageHoldCo;
    /** 땅속 작물 수확기 보유 대수 */
    private String harvestHoldCo;
    /** 탈곡기 및 정선 작업기 보유 대수 */
    private String thresherHoldCo;
    /** 자주형 파종기 보유 대수 */
    private String planterHoldCo;
    /** 이앙 작업기 보유 대수 */
    private String transplantHoldCo;
    /** 벼 수확 및 운반 작업기 보유 대수 */
    private String rcepntHoldCo;
    /** 기타 임대 농기계 보유 정보 */
    private String etcRentHoldCo;
    /** 관리 기관 전화번호 */
    private String phoneNumber;
    /** 관리 기관명 */
    private String institutionNm;
    /** 데이터 기준 일자 */
    private String referenceDate;
    /** 제공 기관 코드 */
    private String instt_code;
}
