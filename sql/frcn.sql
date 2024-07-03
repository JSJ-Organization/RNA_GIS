-- PostGIS 확장 활성화
CREATE EXTENSION IF NOT EXISTS postgis;

CREATE SEQUENCE IF NOT EXISTS frcn_rent_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS frcn_rent_info_log_seq
    INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS frcn_rent_info
(
    id                  BIGINT DEFAULT nextval('frcn_rent_info_id_seq'::regclass) NOT NULL PRIMARY KEY,
    batch_date          VARCHAR(255),
    cultvt_hold_co      VARCHAR(255),
    etc_rent_hold_co    TEXT,
    geometry            GEOMETRY(Point, 4326),
    harvest_hold_co     VARCHAR(255),
    institution_nm      VARCHAR(255),
    instt_code          VARCHAR(255),
    lnmadr              VARCHAR(255),
    manage_hold_co      VARCHAR(255),
    office_nm           VARCHAR(255),
    office_phone_number VARCHAR(255),
    phone_number        VARCHAR(255),
    planter_hold_co     VARCHAR(255),
    rcepnt_hold_co      VARCHAR(255),
    rdnmadr             VARCHAR(255),
    reference_date      VARCHAR(255),
    thresher_hold_co    VARCHAR(255),
    transplant_hold_co  VARCHAR(255),
    trctor_hold_co      VARCHAR(255)
);

ALTER TABLE frcn_rent_info
    ALTER COLUMN id SET DEFAULT nextval('frcn_rent_info_id_seq');

ALTER TABLE frcn_rent_info
    OWNER TO admin;

CREATE TABLE IF NOT EXISTS frcn_rent_info_log
(
    id           BIGINT       NOT NULL PRIMARY KEY,
    created_at   TIMESTAMP(6) NOT NULL,
    error_code   VARCHAR(255),
    error_text   VARCHAR(255),
    search_query VARCHAR(255),
    status       VARCHAR(255)
);

ALTER TABLE frcn_rent_info_log
    OWNER TO admin;

ALTER SEQUENCE frcn_rent_info_log_seq
    OWNER TO admin;
