package com.jsj.backend.search.vworld;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 좌표계를 나타내는 열거형 클래스
 */
@Getter
@AllArgsConstructor
public enum SpatialReferenceSystem {
    /**
     * WGS84 경위도 (EPSG:4326)
     */
    WGS84("EPSG:4326"),

    /**
     * GRS80 경위도 (EPSG:4019)
     */
    GRS80("EPSG:4019"),

    /**
     * Google Mercator (EPSG:3857)
     */
    GOOGLE_MERCATOR_3857("EPSG:3857"),

    /**
     * Google Mercator (EPSG:900913)
     */
    GOOGLE_MERCATOR_900913("EPSG:900913"),

    /**
     * 서부원점 (GRS80) (EPSG:5180, 50만)
     */
    WESTERN_ORIGIN_5180("EPSG:5180"),

    /**
     * 서부원점 (GRS80) (EPSG:5185)
     */
    WESTERN_ORIGIN_5185("EPSG:5185"),

    /**
     * 중부원점 (GRS80) (EPSG:5181, 50만)
     */
    CENTRAL_ORIGIN_5181("EPSG:5181"),

    /**
     * 중부원점 (GRS80) (EPSG:5186)
     */
    CENTRAL_ORIGIN_5186("EPSG:5186"),

    /**
     * 제주원점 (GRS80, 55만) (EPSG:5182)
     */
    JEJU_ORIGIN_5182("EPSG:5182"),

    /**
     * 동부원점 (GRS80) (EPSG:5183, 50만)
     */
    EASTERN_ORIGIN_5183("EPSG:5183"),

    /**
     * 동부원점 (GRS80) (EPSG:5187)
     */
    EASTERN_ORIGIN_5187("EPSG:5187"),

    /**
     * 동해(울릉)원점 (GRS80) (EPSG:5184, 50만)
     */
    DONGHAE_ULLEUNG_ORIGIN_5184("EPSG:5184"),

    /**
     * 동해(울릉)원점 (GRS80) (EPSG:5188)
     */
    DONGHAE_ULLEUNG_ORIGIN_5188("EPSG:5188"),

    /**
     * UTM-K (GRS80) (EPSG:5179)
     */
    UTM_K("EPSG:5179");

    /**
     * 좌표계 코드
     */
    private final String code;
}