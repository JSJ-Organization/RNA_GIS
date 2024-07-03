-- frcn

-- auto-generated definition
create table frcn_rent_info
(
    id                  bigint default nextval('frcn_rent_info_id_seq'::regclass) not null
        primary key,
    batch_date          varchar(255),
    cultvt_hold_co      varchar(255),
    etc_rent_hold_co    text,
    geometry            geometry(Point, 4326),
    harvest_hold_co     varchar(255),
    institution_nm      varchar(255),
    instt_code          varchar(255),
    lnmadr              varchar(255),
    manage_hold_co      varchar(255),
    office_nm           varchar(255),
    office_phone_number varchar(255),
    phone_number        varchar(255),
    planter_hold_co     varchar(255),
    rcepnt_hold_co      varchar(255),
    rdnmadr             varchar(255),
    reference_date      varchar(255),
    thresher_hold_co    varchar(255),
    transplant_hold_co  varchar(255),
    trctor_hold_co      varchar(255)
);

-- seq

alter table frcn_rent_info
    owner to admin;

CREATE SEQUENCE frcn_rent_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE frcn_rent_info ALTER COLUMN id SET DEFAULT nextval('frcn_rent_info_id_seq');