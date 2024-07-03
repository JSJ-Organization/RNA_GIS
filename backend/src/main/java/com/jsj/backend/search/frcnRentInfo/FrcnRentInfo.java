package com.jsj.backend.search.frcnRentInfo;

import com.jsj.backend.util.DateUtil;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Geometry;

/**
 * 농기계 임대 정보 API를 나타내는 엔티티 클래스.
 * 이 클래스는 농기계 임대 정보 데이터를 나타내며, JPA를 사용하여 데이터베이스에 매핑됩니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "frcn_rent_info")
public class FrcnRentInfo {

    /** 고유 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frcn_rent_info_seq")
    @SequenceGenerator(name = "frcn_rent_info_seq", sequenceName = "frcn_rent_info_id_seq", allocationSize = 1)
    private Long id;

    /** 사업소명 */
    private String officeNm;

    /** 사업소 전화번호 */
    private String officePhoneNumber;

    /** 소재지 도로명 주소 */
    private String rdnmadr;

    /** 소재지 지번 주소 */
    private String lnmadr;

    /** 위도, 경도를 포함한 좌표 객체 EPSG:4326*/
    @Column(columnDefinition = "geometry(Point, 4326)")
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
    @Column(columnDefinition = "TEXT")
    private String etcRentHoldCo;

    /** 관리 기관 전화번호 */
    private String phoneNumber;

    /** 관리 기관명 */
    private String institutionNm;

    /** 제공 기관 코드 */
    private String instt_code;

    /** 데이터 기준 일자 */
    private String referenceDate;

    /** Batch Job 시행 일자 */
    @Builder.Default
    private String batchDate = DateUtil.getTodayDateString();
}
