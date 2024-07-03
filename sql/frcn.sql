DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'frcn_rent_info') THEN
            CREATE TABLE frcn_rent_info
            (
                id                  bigint default nextval('frcn_rent_info_id_seq'::regclass) not null primary key,
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
        END IF;

        IF NOT EXISTS (SELECT 1
                       FROM pg_sequences
                       WHERE schemaname = 'public'
                         AND sequencename = 'frcn_rent_info_id_seq') THEN
            CREATE SEQUENCE frcn_rent_info_id_seq
                START WITH 1
                INCREMENT BY 1
                NO MINVALUE
                NO MAXVALUE
                CACHE 1;
        END IF;

        ALTER TABLE frcn_rent_info
            ALTER COLUMN id SET DEFAULT nextval('frcn_rent_info_id_seq');
        ALTER TABLE frcn_rent_info
            OWNER TO admin;

    END
$$;

DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'frcn_rent_info_log') THEN
            CREATE TABLE frcn_rent_info_log
            (
                id           BIGINT       NOT NULL PRIMARY KEY,
                created_at   TIMESTAMP(6) NOT NULL,
                error_code   VARCHAR(255),
                error_text   VARCHAR(255),
                search_query VARCHAR(255),
                status       VARCHAR(255)
            );
        END IF;

        IF NOT EXISTS (SELECT 1
                       FROM pg_sequences
                       WHERE schemaname = 'public' AND sequencename = 'frcn_rent_info_log_seq') THEN
            CREATE SEQUENCE frcn_rent_info_log_seq
                INCREMENT BY 50;
        END IF;

        ALTER TABLE frcn_rent_info_log
            OWNER TO admin;
        ALTER SEQUENCE frcn_rent_info_log_seq OWNER TO admin;

    END
$$;